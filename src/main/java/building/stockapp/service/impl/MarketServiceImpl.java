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

import building.stockapp.dto.StockDashboardDto;
import building.stockapp.dto.YahooStockQuoteDto;
import building.stockapp.model.Stock;
import building.stockapp.repository.StockRepository;
import building.stockapp.service.MarketService;

@Service
public class MarketServiceImpl implements MarketService {

	private StockRepository stockRepository;
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
		StockDashboardDto fetchedDto = StockDashboardDto.builder()
				.stockOverallInvestmentValue(stockOverallInvestmentValue).stockCurrentValue(stockCurrentValue)
				.stockCurrentReturn(stockCurrentReturn).stockCurrentReturnPercent(stockCurrentReturnPercent)
				.stockLastTransactionOn(stockLastTransactionOn).build();
		LOGGER.log(Level.INFO, "Current stock holding dashboard fetched sucessfully");
		return fetchedDto;
	}

	private String getMarketExtension(String market) {
		if (market.equalsIgnoreCase("nse")) {
			return ".NS";
		}
		return market.equalsIgnoreCase("bse") ? ".BO" : "";
	}

	public StockRepository getStockRepository() {
		return stockRepository;
	}

	@Autowired
	public void setStockRepository(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

}
