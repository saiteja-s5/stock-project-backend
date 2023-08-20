package building.sma.market.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import building.sma.market.dto.YahooQuoteDTO;
import building.sma.market.model.HistoricalQuote;
import building.sma.market.model.HistoricalQuoteBody;
import building.sma.market.service.MarketService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/markets")
public class MarketController {

    private MarketService marketService;

    // Single Stock with symbol + market
    @GetMapping("/{market}/{stockSymbol}")
    public ResponseEntity<YahooQuoteDTO> getStockQuote(@PathVariable String market, @PathVariable String stockSymbol) {
	if (market.equalsIgnoreCase("nse")) {
	    log.info(String.format("Request received to fetch %s data from NSE", stockSymbol));
	} else if (market.equalsIgnoreCase("bse")) {
	    log.info(String.format("Request received to fetch %s data from BSE", stockSymbol));
	}
	return new ResponseEntity<>(marketService.getQuote(market, stockSymbol), HttpStatus.OK);
    }

    // Historical Quotes with symbol + startDate + endDate + interval
    @GetMapping("/historical-stock-holdings")
    public ResponseEntity<List<HistoricalQuote>> getHistoricalStockHoldingsQuote(
	    @RequestBody HistoricalQuoteBody template) {
	log.info(String.format("Request received to fetch all historical quotes for stock %s", template.getSymbol()));
	return new ResponseEntity<>(marketService.getHistory(template.getMarket(), template.getSymbol(),
		template.getFrom(), template.getTo(), template.getInterval()), HttpStatus.OK);
    }

}
