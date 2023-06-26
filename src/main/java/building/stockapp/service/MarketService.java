package building.stockapp.service;

import java.util.List;

import building.stockapp.dto.DividendDashboardDto;
import building.stockapp.dto.FundDashboardDto;
import building.stockapp.dto.ProfitLossDashboardDto;
import building.stockapp.dto.StockDashboardDto;
import building.stockapp.dto.YahooQuoteDto;

public interface MarketService {

	YahooQuoteDto getQuote(String market, String symbol);

	List<YahooQuoteDto> getQuotes(String market, List<String> symbols);

	StockDashboardDto getCurrentHoldingStockDashboard(List<String> stockSymbols);

	FundDashboardDto getCurrentHoldingFundDashboard();

	DividendDashboardDto getCurrentHoldingDividendDashboard();

	ProfitLossDashboardDto getCurrentHoldingProfitLossDashboard();

}
