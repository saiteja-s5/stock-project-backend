package building.stockapp.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import building.stockapp.dto.DividendDashboardDto;
import building.stockapp.dto.FundDashboardDto;
import building.stockapp.dto.ProfitLossDashboardDto;
import building.stockapp.dto.StockDashboardDto;
import building.stockapp.dto.YahooQuoteDto;
import building.stockapp.exception.MiscellaneousRecordNotExistException;
import building.stockapp.model.Dividend;
import building.stockapp.model.Fund;
import building.stockapp.model.HistoricalQuote;
import building.stockapp.model.ProfitLoss;
import building.stockapp.model.Stock;
import building.stockapp.repository.DividendRepository;
import building.stockapp.repository.FundRepository;
import building.stockapp.repository.MiscellaneousRecordRepository;
import building.stockapp.repository.ProfitLossRepository;
import building.stockapp.repository.StockRepository;
import building.stockapp.service.MarketService;
import building.stockapp.utility.Interval;
import building.stockapp.utility.Utility;
import lombok.Getter;

@Getter
@Service
public class MarketServiceImpl implements MarketService {

	private StockRepository stockRepository;
	private FundRepository fundRepository;
	private DividendRepository dividendRepository;
	private ProfitLossRepository profitLossRepository;
	private MiscellaneousRecordRepository miscRecordRepository;

	private static final Logger LOGGER = Logger.getLogger(MarketServiceImpl.class.getName());
	private static String crumb = "dKq4FFFb9Xz";
	private static String cookie = "A1=d=AQABBAVGnWMCEMQWQR36X4J3ijLpLPruWaYFEgEBCAGuhmS2ZFlQb2UB_eMBAAcIBUadY_ruWaY&S=AQAAAhy9uDwG2WFWWTzYTd-bT6Y; A3=d=AQABBAVGnWMCEMQWQR36X4J3ijLpLPruWaYFEgEBCAGuhmS2ZFlQb2UB_eMBAAcIBUadY_ruWaY&S=AQAAAhy9uDwG2WFWWTzYTd-bT6Y; cmp=t=1686463791&j=0&u=1---; gam_id=y-dgF31zJE2uKlSQ7em1AyJvOfFxbN45Oc~A; B=acmfev9hpqhg5&b=3&s=3p; GUC=AQEBCAFkhq5ktkIjtAT5; PRF=t%3DAPI%26newChartbetateaser%3D0%252C1687673503957; tbla_id=60e25e2e-9d52-41f6-8102-b1f281df14e9-tuctb7eeb33; A1S=d=AQABBAVGnWMCEMQWQR36X4J3ijLpLPruWaYFEgEBCAGuhmS2ZFlQb2UB_eMBAAcIBUadY_ruWaY&S=AQAAAhy9uDwG2WFWWTzYTd-bT6Y&j=WORLD";

	@Override
	public YahooQuoteDto getQuote(String market, String symbol) {
		String marketExtension = getMarketExtension(market);
		URIBuilder builder = new URIBuilder().setScheme("https").setHost("query1.finance.yahoo.com")
				.setPath("/v8/finance/quote").addParameter("symbols", symbol + marketExtension)
				.addParameter("crumb", crumb);
		try {
			URI uri = builder.build();
			LOGGER.log(Level.INFO, "Dispatching request to {0}", uri);
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder(uri).GET().header("Cookie", cookie).build();
			JSONObject response = new JSONObject(client.send(request, HttpResponse.BodyHandlers.ofString()).body());
			LOGGER.log(Level.INFO, "Response received from {0}", uri);
			String quoteAsString = response.getJSONObject("quoteResponse").getJSONArray("result").get(0).toString();
			YahooQuoteDto fetchedResponse = new ObjectMapper().readValue(quoteAsString, YahooQuoteDto.class);
			LOGGER.log(Level.INFO, "Response mapping to DTO is completed");
			return fetchedResponse;
		} catch (InterruptedException ie) {
			LOGGER.log(Level.WARNING, "Interrupted Exception occured {0}", ie.getMessage());
			Thread.currentThread().interrupt();
		} catch (JSONException | IOException | URISyntaxException e) {
			LOGGER.log(Level.WARNING, "Exception occured {0}", e.getMessage());
		}
		return new YahooQuoteDto();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistoricalQuote> getHistory(String market, String symbol, LocalDate from, LocalDate to,
			Interval interval) {
		List<HistoricalQuote> history = new ArrayList<>();
		String marketExtension = getMarketExtension(market);
		URIBuilder builder = new URIBuilder().setScheme("https").setHost("query1.finance.yahoo.com")
				.setPath("/v8/finance/chart/" + symbol + marketExtension).addParameter("crumb", crumb)
				.addParameter("includeAdjustedClose", "true").addParameter("interval", interval.getTag())
				.addParameter("period1", String.valueOf(Utility.localDateToEpochSecond(from)))
				.addParameter("period2", String.valueOf(Utility.localDateToEpochSecond(to.plusDays(1))));
		try {
			URI uri = builder.build();
			LOGGER.log(Level.INFO, "Dispatching request to {0}", uri);
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder(uri).GET().header("Cookie", cookie).build();
			JSONObject response = new JSONObject(client.send(request, HttpResponse.BodyHandlers.ofString()).body());
			LOGGER.log(Level.INFO, "Response received from {0}", uri);
			JSONObject intermediateJson = response.getJSONObject("chart").getJSONArray("result").getJSONObject(0);
			Map<String, List<BigDecimal>> opLoHiClAdjClMap = (Map<String, List<BigDecimal>>) intermediateJson
					.getJSONObject("indicators").getJSONArray("quote").toList().get(0);
			Map<String, List<BigDecimal>> adjCloseMap = (Map<String, List<BigDecimal>>) intermediateJson
					.getJSONObject("indicators").getJSONArray("adjclose").toList().get(0);
			List<Object> tradedDatesInObject = intermediateJson.getJSONArray("timestamp").toList();
			List<BigDecimal> opens = opLoHiClAdjClMap.get("open");
			List<BigDecimal> closes = opLoHiClAdjClMap.get("close");
			List<BigDecimal> lows = opLoHiClAdjClMap.get("low");
			List<BigDecimal> highs = opLoHiClAdjClMap.get("high");
			List<Integer> volumes = ((Map<String, List<Integer>>) (intermediateJson.getJSONObject("indicators")
					.getJSONArray("quote").toList().get(0))).get("volume");
			List<BigDecimal> adjCloses = adjCloseMap.get("adjclose");
			LOGGER.log(Level.INFO, "Response mapped to List");
			for (int i = 0; i < tradedDatesInObject.size(); i++) {
				history.add(HistoricalQuote.builder()
						.tradedDate(Utility.epochSecondToLocalDate((int) tradedDatesInObject.get(i))).open(opens.get(i))
						.close(closes.get(i)).low(lows.get(i)).high(highs.get(i)).adjustedClose(adjCloses.get(i))
						.volume((int) volumes.get(i)).build());
			}
			LOGGER.log(Level.INFO, "Mapping from List to Dto list completed");
			return history;
		} catch (InterruptedException ie) {
			LOGGER.log(Level.WARNING, "Interrupted Exception occured {0}", ie.getMessage());
			Thread.currentThread().interrupt();
		} catch (JSONException | IOException | URISyntaxException e) {
			LOGGER.log(Level.WARNING, "Exception occured {0}", e.getMessage());
		}
		return history;
	}

	@Override
	public List<YahooQuoteDto> getQuotes(String market, List<String> symbols) {
		LOGGER.log(Level.INFO, "Getting all stock quotes");
		List<YahooQuoteDto> stocks = new ArrayList<>();
		symbols.forEach(stockSymbol -> stocks.add(getQuote(market, stockSymbol)));
		LOGGER.log(Level.INFO, "Retrieved {0} stock quotes sucessfully", stocks.size());
		return stocks;
	}

	@Override
	public StockDashboardDto getCurrentHoldingStockDashboard(List<String> stockSymbols) {
		LOGGER.log(Level.INFO, "Getting current stock holding dashboard");
		List<Stock> boughtStocks = stockRepository.findAll();
		Double stockInvestmentValue = boughtStocks.stream()
				.mapToDouble(stock -> stock.getBuyPrice() * stock.getQuantity()).sum();
		Double stockCurrentValue = boughtStocks.stream()
				.mapToDouble(
						stock -> getQuote("NSE", stock.getStockName()).getRegularMarketPrice() * stock.getQuantity())
				.sum();
		Double stockCurrentReturn = stockCurrentValue - stockInvestmentValue;
		Double stockCurrentReturnPercent = (stockCurrentReturn * 100) / stockCurrentValue;
		Optional<Stock> stock = boughtStocks.stream().max(Comparator.comparing(Stock::getInvestmentDate));
		LocalDate stockLastTransactionOn = stock.isPresent() ? stock.get().getInvestmentDate() : null;
		LocalDate stockTableUpdatedOn = miscRecordRepository.findById(Utility.MISC_TABLE_PRIMARY_KEY)
				.orElseThrow(MiscellaneousRecordNotExistException::new).getStockTableUpdatedOn();
		StockDashboardDto fetchedDto = StockDashboardDto.builder().stockInvestmentValue(stockInvestmentValue)
				.stockCurrentValue(stockCurrentValue).stockCurrentReturn(stockCurrentReturn)
				.stockCurrentReturnPercent(stockCurrentReturnPercent).stockLastTransactionOn(stockLastTransactionOn)
				.stockTableUpdatedOn(stockTableUpdatedOn).build();
		LOGGER.log(Level.INFO, "Current stock holding dashboard fetched sucessfully");
		return fetchedDto;
	}

	@Override
	public FundDashboardDto getCurrentHoldingFundDashboard() {
		LOGGER.log(Level.INFO, "Getting current fund holding dashboard");
		List<Fund> allFunds = fundRepository.findAll();
		Double overallCreditedAmount = allFunds.stream().mapToDouble(Fund::getCreditedAmount).sum();
		Double overallDebitedAmount = allFunds.stream().mapToDouble(Fund::getDebitedAmount).sum();
		Double overallCashIn = overallCreditedAmount - overallDebitedAmount;
		Optional<Fund> fund = allFunds.stream().max(Comparator.comparing(Fund::getTransactionDate));
		LocalDate fundLastTransactionOn = fund.isPresent() ? fund.get().getTransactionDate() : null;
		LocalDate fundTableUpdatedOn = miscRecordRepository.findById(Utility.MISC_TABLE_PRIMARY_KEY)
				.orElseThrow(MiscellaneousRecordNotExistException::new).getFundTableUpdatedOn();
		FundDashboardDto fetchedDto = FundDashboardDto.builder().overallCashIn(overallCashIn)
				.overallCreditedAmount(overallCreditedAmount).overallDebitedAmount(overallDebitedAmount)
				.fundLastTransactionOn(fundLastTransactionOn).fundTableUpdatedOn(fundTableUpdatedOn).build();
		LOGGER.log(Level.INFO, "Current fund holding dashboard fetched sucessfully");
		return fetchedDto;
	}

	@Override
	public DividendDashboardDto getCurrentHoldingDividendDashboard() {
		LOGGER.log(Level.INFO, "Getting current dividend holding dashboard");
		List<Dividend> allDividends = dividendRepository.findAll();
		Double dividendEarnedOverall = allDividends.stream().mapToDouble(Dividend::getCreditedAmount).sum();
		Optional<Dividend> dividend = allDividends.stream().max(Comparator.comparing(Dividend::getCreditedDate));
		LocalDate dividendLastTransactionOn = dividend.isPresent() ? dividend.get().getCreditedDate() : null;
		LocalDate dividendTableUpdatedOn = miscRecordRepository.findById(Utility.MISC_TABLE_PRIMARY_KEY)
				.orElseThrow(MiscellaneousRecordNotExistException::new).getDividendTableUpdatedOn();
		DividendDashboardDto fetchedDto = DividendDashboardDto.builder().dividendEarnedOverall(dividendEarnedOverall)
				.dividendLastTransactionOn(dividendLastTransactionOn).dividendTableUpdatedOn(dividendTableUpdatedOn)
				.build();
		LOGGER.log(Level.INFO, "Current dividend holding dashboard fetched sucessfully");
		return fetchedDto;
	}

	@Override
	public ProfitLossDashboardDto getCurrentHoldingProfitLossDashboard() {
		LOGGER.log(Level.INFO, "Getting current profit and loss holding dashboard");
		List<ProfitLoss> allpls = profitLossRepository.findAll();
		Double overallBoughtAmount = allpls.stream().mapToDouble(pl -> pl.getBought().getBoughtPrice()).sum();
		Double overallSoldAmount = allpls.stream().mapToDouble(pl -> pl.getSold().getSoldPrice()).sum();
		Double profitLossEarnedOverall = overallSoldAmount - overallBoughtAmount;
		Comparator<ProfitLoss> lastDateComparator = (pl1, pl2) -> pl1.getSold().getSoldDate()
				.compareTo(pl2.getSold().getSoldDate());
		Optional<ProfitLoss> pl = allpls.stream().max(lastDateComparator);
		LocalDate profitLossLastTransactionOn = pl.isPresent() ? pl.get().getSold().getSoldDate() : null;
		LocalDate plTableUpdatedOn = miscRecordRepository.findById(Utility.MISC_TABLE_PRIMARY_KEY)
				.orElseThrow(MiscellaneousRecordNotExistException::new).getProfitLossTableUpdatedOn();
		ProfitLossDashboardDto fetchedDto = ProfitLossDashboardDto.builder()
				.profitLossEarnedOverall(profitLossEarnedOverall).overallBoughtAmount(overallBoughtAmount)
				.overallSoldAmount(overallSoldAmount).profitLossLastTransactionOn(profitLossLastTransactionOn)
				.profitLossTableUpdatedOn(plTableUpdatedOn).build();
		LOGGER.log(Level.INFO, "Current profit and loss holding dashboard fetched sucessfully");
		return fetchedDto;
	}

	private String getMarketExtension(String market) {
		if (market.equalsIgnoreCase("nse")) {
			return ".NS";
		}
		return market.equalsIgnoreCase("bse") ? ".BO" : "";
	}

	@Autowired
	public void setStockRepository(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	@Autowired
	public void setFundRepository(FundRepository fundRepository) {
		this.fundRepository = fundRepository;
	}

	@Autowired
	public void setDividendRepository(DividendRepository dividendRepository) {
		this.dividendRepository = dividendRepository;
	}

	@Autowired
	public void setProfitLossRepository(ProfitLossRepository profitLossRepository) {
		this.profitLossRepository = profitLossRepository;
	}

	@Autowired
	public void setMiscRecordRepository(MiscellaneousRecordRepository miscRecordRepository) {
		this.miscRecordRepository = miscRecordRepository;
	}

}
