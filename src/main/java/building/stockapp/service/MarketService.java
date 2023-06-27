package building.stockapp.service;

import java.time.LocalDate;
import java.util.List;

import building.stockapp.dto.DividendDashboardDto;
import building.stockapp.dto.FundDashboardDto;
import building.stockapp.dto.ProfitLossDashboardDto;
import building.stockapp.dto.StockDashboardDto;
import building.stockapp.dto.YahooQuoteDto;
import building.stockapp.model.HistoricalQuote;
import building.stockapp.utility.Interval;

public interface MarketService {

	YahooQuoteDto getQuote(String market, String symbol);

	List<YahooQuoteDto> getQuotes(String market, List<String> symbols);

	List<HistoricalQuote> getHistory(String market, String symbol, LocalDate from, LocalDate to, Interval interval);

	StockDashboardDto getCurrentHoldingStockDashboard(List<String> stockSymbols);

	FundDashboardDto getCurrentHoldingFundDashboard();

	DividendDashboardDto getCurrentHoldingDividendDashboard();

	ProfitLossDashboardDto getCurrentHoldingProfitLossDashboard();

}
