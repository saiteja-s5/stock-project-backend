package building.sma.market.service;

import java.time.LocalDate;
import java.util.List;

import building.sma.market.dto.HistoricalQuote;
import building.sma.market.dto.YahooQuoteDTO;
import building.sma.market.model.Interval;
import building.sma.market.model.Market;

public interface MarketService {

    YahooQuoteDTO getQuote(Market market, String symbol);

    List<HistoricalQuote> getQuoteHistory(Market market, String symbol, LocalDate from, LocalDate to,
	    Interval interval);

}
