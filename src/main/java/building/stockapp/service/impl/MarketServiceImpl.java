package building.stockapp.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
import building.stockapp.dto.YahooStockQuoteDto;
import building.stockapp.exception.MiscellaneousRecordNotExistException;
import building.stockapp.model.Dividend;
import building.stockapp.model.Fund;
import building.stockapp.model.ProfitLoss;
import building.stockapp.model.Stock;
import building.stockapp.repository.DividendRepository;
import building.stockapp.repository.FundRepository;
import building.stockapp.repository.MiscellaneousRecordRepository;
import building.stockapp.repository.ProfitLossRepository;
import building.stockapp.repository.StockRepository;
import building.stockapp.service.MarketService;
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

	public YahooStockQuoteDto getStockQuote(String market, String stockSymbol) {

		String crumb = "dKq4FFFb9Xz";
		String cookie = "A1=d=AQABBAVGnWMCEMQWQR36X4J3ijLpLPruWaYFEgEBCAGuhmS2ZFlQb2UB_eMBAAcIBUadY_ruWaY&S=AQAAAhy9uDwG2WFWWTzYTd-bT6Y; A3=d=AQABBAVGnWMCEMQWQR36X4J3ijLpLPruWaYFEgEBCAGuhmS2ZFlQb2UB_eMBAAcIBUadY_ruWaY&S=AQAAAhy9uDwG2WFWWTzYTd-bT6Y; cmp=t=1686463791&j=0&u=1---; gam_id=y-dgF31zJE2uKlSQ7em1AyJvOfFxbN45Oc~A; B=acmfev9hpqhg5&b=3&s=3p; GUC=AQEBCAFkhq5ktkIjtAT5; PRF=t%3DAPI%26newChartbetateaser%3D0%252C1687673503957; tbla_id=60e25e2e-9d52-41f6-8102-b1f281df14e9-tuctb7eeb33; A1S=d=AQABBAVGnWMCEMQWQR36X4J3ijLpLPruWaYFEgEBCAGuhmS2ZFlQb2UB_eMBAAcIBUadY_ruWaY&S=AQAAAhy9uDwG2WFWWTzYTd-bT6Y&j=WORLD";

		String marketExtension = getMarketExtension(market);

		URIBuilder builder = new URIBuilder().setScheme("https").setHost("query1.finance.yahoo.com")
				.setPath("/v7/finance/quote").addParameter("symbols", stockSymbol + marketExtension)
				.addParameter("crumb", crumb);
		try {
			URI uri = builder.build();
			LOGGER.log(Level.INFO, "Dispatching request to {0}", uri);
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder(uri).GET().header("Cookie", cookie).build();
			JSONObject response = new JSONObject(client.send(request, HttpResponse.BodyHandlers.ofString()).body());
			LOGGER.log(Level.INFO, "Response received from {0}", uri);
			String quoteAsString = response.getJSONObject("quoteResponse").getJSONArray("result").get(0).toString();
			YahooStockQuoteDto fetchedResponse = new ObjectMapper().readValue(quoteAsString, YahooStockQuoteDto.class);
			LOGGER.log(Level.INFO, "Response mapping to DTO is completed");
			return fetchedResponse;
		} catch (InterruptedException ie) {
			LOGGER.log(Level.WARNING, "Interrupted Exception occured {0}", ie.getMessage());
			Thread.currentThread().interrupt();
		} catch (JSONException | IOException | URISyntaxException e) {
			LOGGER.log(Level.WARNING, "Exception occured {0}", e.getMessage());
		}
		return new YahooStockQuoteDto();
	}

	@Override
	public List<YahooStockQuoteDto> getStocksQuote(String market, List<String> stockSymbols) {
		LOGGER.log(Level.INFO, "Getting all stock quotes");
		List<YahooStockQuoteDto> stocks = new ArrayList<>();
		stockSymbols.forEach(stockSymbol -> stocks.add(getStockQuote(market, stockSymbol)));
		LOGGER.log(Level.INFO, "Retrieved {0} stock quotes sucessfully", stocks.size());
		return stocks;
	}

	@Override
	public StockDashboardDto getCurrentHoldingStockDashboard(List<String> stockSymbols) {
		LOGGER.log(Level.INFO, "Getting current stock holding dashboard");
		List<Stock> boughtStocks = stockRepository.findAll();
		Double stockOverallInvestmentValue = boughtStocks.stream()
				.mapToDouble(stock -> stock.getBuyPrice() * stock.getQuantity()).sum();
		Double stockCurrentValue = boughtStocks.stream().mapToDouble(
				stock -> getStockQuote("NSE", stock.getStockName()).getRegularMarketPrice() * stock.getQuantity())
				.sum();
		Double stockCurrentReturn = stockCurrentValue - stockOverallInvestmentValue;
		Double stockCurrentReturnPercent = (stockCurrentReturn * 100) / stockCurrentValue;
		Optional<Stock> stock = boughtStocks.stream().max(Comparator.comparing(Stock::getInvestmentDate));
		LocalDate stockLastTransactionOn = stock.isPresent() ? stock.get().getInvestmentDate() : null;
		LocalDate stockTableUpdatedOn = miscRecordRepository.findById(Utility.MISC_TABLE_PRIMARY_KEY)
				.orElseThrow(MiscellaneousRecordNotExistException::new).getStockTableUpdatedOn();
		StockDashboardDto fetchedDto = StockDashboardDto.builder()
				.stockOverallInvestmentValue(stockOverallInvestmentValue).stockCurrentValue(stockCurrentValue)
				.stockCurrentReturn(stockCurrentReturn).stockCurrentReturnPercent(stockCurrentReturnPercent)
				.stockLastTransactionOn(stockLastTransactionOn).stockTableLastUpdatedOn(stockTableUpdatedOn).build();
		LOGGER.log(Level.INFO, "Current stock holding dashboard fetched sucessfully");
		return fetchedDto;
	}

	@Override
	public FundDashboardDto getCurrentHoldingFundDashboard() {
		LOGGER.log(Level.INFO, "Getting current fund holding dashboard");
		List<Fund> allFunds = fundRepository.findAll();
		Double fundOverallCreditedAmount = allFunds.stream().mapToDouble(Fund::getCreditedAmount).sum();
		Double fundOverallDebitedAmount = allFunds.stream().mapToDouble(Fund::getDebitedAmount).sum();
		Double fundOverallCashIn = fundOverallCreditedAmount - fundOverallDebitedAmount;
		Optional<Fund> fund = allFunds.stream().max(Comparator.comparing(Fund::getTransactionDate));
		LocalDate fundLastTransactionOn = fund.isPresent() ? fund.get().getTransactionDate() : null;
		LocalDate fundTableUpdatedOn = miscRecordRepository.findById(Utility.MISC_TABLE_PRIMARY_KEY)
				.orElseThrow(MiscellaneousRecordNotExistException::new).getFundTableUpdatedOn();
		FundDashboardDto fetchedDto = FundDashboardDto.builder().fundOverallCashIn(fundOverallCashIn)
				.fundOverallCreditedAmount(fundOverallCreditedAmount).fundOverallDebitedAmount(fundOverallDebitedAmount)
				.fundLastTransactionOn(fundLastTransactionOn).fundTableLastUpdatedOn(fundTableUpdatedOn).build();
		LOGGER.log(Level.INFO, "Current fund holding dashboard fetched sucessfully");
		return fetchedDto;
	}

	@Override
	public DividendDashboardDto getCurrentHoldingDividendDashboard() {
		LOGGER.log(Level.INFO, "Getting current dividend holding dashboard");
		List<Dividend> allDividends = dividendRepository.findAll();
		Double dividendOverallEarned = allDividends.stream().mapToDouble(Dividend::getCreditedAmount).sum();
		Optional<Dividend> dividend = allDividends.stream().max(Comparator.comparing(Dividend::getCreditedDate));
		LocalDate dividendLastTransactionOn = dividend.isPresent() ? dividend.get().getCreditedDate() : null;
		LocalDate dividendTableUpdatedOn = miscRecordRepository.findById(Utility.MISC_TABLE_PRIMARY_KEY)
				.orElseThrow(MiscellaneousRecordNotExistException::new).getDividendTableUpdatedOn();
		DividendDashboardDto fetchedDto = DividendDashboardDto.builder().dividendOverallEarned(dividendOverallEarned)
				.dividendLastTransactionOn(dividendLastTransactionOn).dividendTableLastUpdatedOn(dividendTableUpdatedOn)
				.build();
		LOGGER.log(Level.INFO, "Current dividend holding dashboard fetched sucessfully");
		return fetchedDto;
	}

	@Override
	public ProfitLossDashboardDto getCurrentHoldingProfitLossDashboard() {
		LOGGER.log(Level.INFO, "Getting current profit and loss holding dashboard");
		List<ProfitLoss> allpls = profitLossRepository.findAll();
		Double plOverallBoughtAmount = allpls.stream().mapToDouble(pl -> pl.getBought().getBoughtPrice()).sum();
		Double plOverallSoldAmount = allpls.stream().mapToDouble(pl -> pl.getSold().getSoldPrice()).sum();
		Double plOverallAmount = plOverallSoldAmount - plOverallBoughtAmount;
		Comparator<ProfitLoss> lastDateComparator = (pl1, pl2) -> pl1.getSold().getSoldDate()
				.compareTo(pl2.getSold().getSoldDate());
		Optional<ProfitLoss> pl = allpls.stream().max(lastDateComparator);
		LocalDate plLastTransactionOn = pl.isPresent() ? pl.get().getSold().getSoldDate() : null;
		LocalDate plTableUpdatedOn = miscRecordRepository.findById(Utility.MISC_TABLE_PRIMARY_KEY)
				.orElseThrow(MiscellaneousRecordNotExistException::new).getProfitLossTableUpdatedOn();
		ProfitLossDashboardDto fetchedDto = ProfitLossDashboardDto.builder().plOverallAmount(plOverallAmount)
				.plOverallBoughtAmount(plOverallBoughtAmount).plOverallSoldAmount(plOverallSoldAmount)
				.plLastTransactionOn(plLastTransactionOn).plTableLastUpdatedOn(plTableUpdatedOn).build();
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
