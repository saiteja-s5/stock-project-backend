package building.stockapp.service;

import java.util.List;

import building.stockapp.dto.YahooStockQuoteDto;

public interface MarketService {

	YahooStockQuoteDto getStockQuote(String market, String stockSymbol);

	List<YahooStockQuoteDto> getStocksQuote(String market, List<String> stockSymbols);

}
