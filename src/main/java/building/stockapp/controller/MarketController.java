package building.stockapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import building.stockapp.dto.DividendDashboardDTO;
import building.stockapp.dto.FundDashboardDTO;
import building.stockapp.dto.ProfitLossDashboardDTO;
import building.stockapp.dto.StockDashboardDTO;
import building.stockapp.dto.YahooQuoteDTO;
import building.stockapp.model.HistoricalQuote;
import building.stockapp.model.HistoricalQuoteBody;
import building.stockapp.service.CompanyNameDropdownService;
import building.stockapp.service.MarketService;
import building.stockapp.service.StockService;

@CrossOrigin
@RestController
@RequestMapping("/markets")
public class MarketController {

	private MarketService marketService;
	private StockService stockService;
	private CompanyNameDropdownService companyNameDropdownService;
	private static final Logger LOGGER = Logger.getLogger(MarketController.class.getName());

	@Autowired
	public MarketController(MarketService marketService, StockService stockService,
			CompanyNameDropdownService companyNameDropdownService) {
		this.marketService = marketService;
		this.stockService = stockService;
		this.companyNameDropdownService = companyNameDropdownService;
	}

	// Single Stock with symbol + market
	@GetMapping("/{market}/{stockSymbol}")
	public ResponseEntity<YahooQuoteDTO> getStockQuote(@PathVariable String market, @PathVariable String stockSymbol) {
		if (market.equalsIgnoreCase("nse")) {
			LOGGER.log(Level.INFO, "Request received to fetch {0} data from NSE", stockSymbol);
		} else if (market.equalsIgnoreCase("bse")) {
			LOGGER.log(Level.INFO, "Request received to fetch {0} data from BSE", stockSymbol);
		}
		return new ResponseEntity<>(marketService.getQuote(market, stockSymbol), HttpStatus.OK);
	}

	// Multiple (All) Stocks from market
	@GetMapping("/{market}")
	public ResponseEntity<List<YahooQuoteDTO>> getAllStocksQuote(@PathVariable String market) {
		LOGGER.log(Level.INFO, "Request received to fetch all stock quotes from {0}", market);
		List<String> allStockSymbols = new ArrayList<>();
		companyNameDropdownService.getCompanyNameDropdowns()
				.forEach(dropdown -> allStockSymbols.add(dropdown.getCompanySymbol()));
		return new ResponseEntity<>(marketService.getQuotes(market, allStockSymbols), HttpStatus.OK);
	}

	// Multiple (Current Holdings) Stocks
	@GetMapping("/current-stock-holdings")
	public ResponseEntity<List<YahooQuoteDTO>> getCurrentStockHoldingsQuote() {
		LOGGER.log(Level.INFO, "Request received to fetch all current stock holding quotes");
		List<String> currentHoldingStockSymbols = new ArrayList<>();
		stockService.getStocks().forEach(stock -> currentHoldingStockSymbols.add(stock.getStockName()));
		return new ResponseEntity<>(marketService.getQuotes("nse", currentHoldingStockSymbols), HttpStatus.OK);
	}

	// Historical Quotes with symbol + startDate + endDate + interval
	@GetMapping("/historical-stock-holdings")
	public ResponseEntity<List<HistoricalQuote>> getHistoricalStockHoldingsQuote(
			@RequestBody HistoricalQuoteBody template) {
		LOGGER.log(Level.INFO, "Request received to fetch all historical quotes for stock {0}", template.getSymbol());
		return new ResponseEntity<>(marketService.getHistory(template.getMarket(), template.getSymbol(),
				template.getFrom(), template.getTo(), template.getInterval()), HttpStatus.OK);
	}

	// Current Stock Holding Dashboard
	@GetMapping("/stock-dashboard")
	public ResponseEntity<StockDashboardDTO> getCurrentHoldingStockDashboard() {
		LOGGER.log(Level.INFO, "Request received to fetch current stock holding dashboard");
		List<String> currentHoldingStockSymbols = new ArrayList<>();
		stockService.getStocks().forEach(stock -> currentHoldingStockSymbols.add(stock.getStockName()));
		return new ResponseEntity<>(marketService.getCurrentHoldingStockDashboard(currentHoldingStockSymbols),
				HttpStatus.OK);
	}

	// Current Fund Holding Dashboard
	@GetMapping("/fund-dashboard")
	public ResponseEntity<FundDashboardDTO> getCurrentHoldingFundDashboard() {
		LOGGER.log(Level.INFO, "Request received to fetch current fund holding dashboard");
		return new ResponseEntity<>(marketService.getCurrentHoldingFundDashboard(), HttpStatus.OK);
	}

	// Current Dividend Holding Dashboard
	@GetMapping("/dividend-dashboard")
	public ResponseEntity<DividendDashboardDTO> getCurrentHoldingDividendDashboard() {
		LOGGER.log(Level.INFO, "Request received to fetch current dividend holding dashboard");
		return new ResponseEntity<>(marketService.getCurrentHoldingDividendDashboard(), HttpStatus.OK);
	}

	// Current Profit Loss Holding Dashboard
	@GetMapping("/profit-loss-dashboard")
	public ResponseEntity<ProfitLossDashboardDTO> getCurrentHoldingProfitLossDashboard() {
		LOGGER.log(Level.INFO, "Request received to fetch current profit and loss holding dashboard");
		return new ResponseEntity<>(marketService.getCurrentHoldingProfitLossDashboard(), HttpStatus.OK);
	}

}
