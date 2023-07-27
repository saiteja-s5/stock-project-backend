package building.stockapp.service;

import java.time.LocalDate;
import java.util.List;

import building.stockapp.dto.DividendDashboardDTO;
import building.stockapp.dto.FundDashboardDTO;
import building.stockapp.dto.ProfitLossDashboardDTO;
import building.stockapp.dto.StockDashboardDTO;
import building.stockapp.dto.YahooQuoteDTO;
import building.stockapp.model.HistoricalQuote;
import building.stockapp.utility.Interval;

public interface MarketService {

	YahooQuoteDTO getQuote(String market, String symbol);

	List<YahooQuoteDTO> getQuotes(String market, List<String> symbols);

	List<HistoricalQuote> getHistory(String market, String symbol, LocalDate from, LocalDate to, Interval interval);

	StockDashboardDTO getCurrentHoldingStockDashboard(List<String> stockSymbols);

	FundDashboardDTO getCurrentHoldingFundDashboard();

	DividendDashboardDTO getCurrentHoldingDividendDashboard();

	ProfitLossDashboardDTO getCurrentHoldingProfitLossDashboard();

}
