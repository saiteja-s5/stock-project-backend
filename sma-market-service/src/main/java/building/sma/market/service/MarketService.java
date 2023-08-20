package building.sma.market.service;

import java.time.LocalDate;
import java.util.List;

import building.sma.market.dto.YahooQuoteDTO;
import building.sma.market.model.HistoricalQuote;
import building.sma.market.model.Interval;

public interface MarketService {

    YahooQuoteDTO getQuote(String market, String symbol);

    List<YahooQuoteDTO> getQuotes(String market, List<String> symbols);

    List<HistoricalQuote> getHistory(String market, String symbol, LocalDate from, LocalDate to, Interval interval);

}
