package building.sma.market.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import building.sma.market.dto.HistoricalQuote;
import building.sma.market.dto.YahooQuoteDTO;
import building.sma.market.model.Interval;
import building.sma.market.model.Market;
import building.sma.market.service.MarketService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/markets")
public class MarketController {

    private final MarketService marketService;

    // Single Stock Quote with market + symbol
    @GetMapping("/quote")
    public ResponseEntity<YahooQuoteDTO> getStockQuote(@RequestParam Optional<Market> market,
	    @RequestParam String symbol) {
	Market tempMarket = market.isPresent() ? market.get() : Market.NSE;
	log.info("Request received to fetch {} data from {}", symbol, tempMarket);
	return new ResponseEntity<>(marketService.getQuote(tempMarket, symbol), HttpStatus.OK);
    }

    // Single Stock Historical Quotes with
    // market + symbol + startDate + endDate + interval
    @GetMapping("/quote-history")
    public ResponseEntity<List<HistoricalQuote>> getHistoricalStockHoldingsQuote(@RequestParam Optional<Market> market,
	    @RequestParam String symbol, @RequestParam Optional<LocalDate> from, @RequestParam Optional<LocalDate> to,
	    @RequestParam Optional<Interval> interval) {
	Market tempMarket = market.isPresent() ? market.get() : Market.NSE;
	LocalDate tempFrom = from.isPresent() ? from.get() : LocalDate.now().minusDays(1);
	LocalDate tempTo = to.isPresent() ? to.get() : LocalDate.now();
	Interval tempInterval = interval.isPresent() ? interval.get() : Interval.DAILY;
	log.info("Request received to fetch historical quote data for {} from {}", symbol, tempMarket);
	return new ResponseEntity<>(marketService.getQuoteHistory(tempMarket, symbol, tempFrom, tempTo, tempInterval),
		HttpStatus.OK);
    }

}
