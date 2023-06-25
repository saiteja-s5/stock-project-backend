package building.stockapp.service;

import java.util.List;

import building.stockapp.dto.DividendDashboardDto;
import building.stockapp.dto.FundDashboardDto;
import building.stockapp.dto.ProfitLossDashboardDto;
import building.stockapp.dto.StockDashboardDto;
import building.stockapp.dto.YahooStockQuoteDto;

public interface MarketService {

	YahooStockQuoteDto getStockQuote(String market, String stockSymbol);

	List<YahooStockQuoteDto> getStocksQuote(String market, List<String> stockSymbols);

	StockDashboardDto getCurrentHoldingStockDashboard(List<String> stockSymbols);

	FundDashboardDto getCurrentHoldingFundDashboard();

	DividendDashboardDto getCurrentHoldingDividendDashboard();

	ProfitLossDashboardDto getCurrentHoldingProfitLossDashboard();

}
