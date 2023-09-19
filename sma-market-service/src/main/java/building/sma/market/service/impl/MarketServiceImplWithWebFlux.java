package building.sma.market.service.impl;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import building.sma.market.dto.HistoricalQuote;
import building.sma.market.dto.YahooFinanceHistoricalResponseDTO;
import building.sma.market.dto.YahooFinanceHistoricalResponseInner2DTO;
import building.sma.market.dto.YahooFinanceHistoricalResponseInner6DTO;
import building.sma.market.dto.YahooFinanceHistoricalResponseInner7DTO;
import building.sma.market.dto.YahooFinanceHistoricalResponseInner8DTO;
import building.sma.market.dto.YahooFinanceResponseDTO;
import building.sma.market.dto.YahooQuoteDTO;
import building.sma.market.exception.ResourceNotFetchedException;
import building.sma.market.model.Interval;
import building.sma.market.model.Market;
import building.sma.market.model.YahooFinanceApiRequestProperties;
import building.sma.market.service.MarketService;
import building.sma.market.utility.MathUtility;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@AllArgsConstructor
public class MarketServiceImplWithWebFlux implements MarketService {

    private final YahooFinanceApiRequestProperties requestProperties;

    private final WebClient.Builder builder;

    @Override
    public YahooQuoteDTO getQuote(Market market, String symbol) {
	URIBuilder uriBuilder = new URIBuilder().setScheme(requestProperties.getScheme())
		.setHost(requestProperties.getHost()).setPath(requestProperties.getQuotePath())
		.addParameter("symbols", symbol + market.getExtension())
		.addParameter("crumb", requestProperties.getCrumb());
	try {
	    YahooFinanceResponseDTO response = builder.build().get().uri(uriBuilder.build())
		    .header("Cookie", requestProperties.getCookie()).retrieve()
		    .bodyToMono(YahooFinanceResponseDTO.class).block();
	    YahooQuoteDTO fetchedResponse = new YahooQuoteDTO();
	    if (response != null) {
		fetchedResponse = response.getQuoteResponse().getResult().get(0);
		log.debug("Response mapping to DTO is completed");
	    }
	    return fetchedResponse;
	} catch (URISyntaxException e) {
	    log.warn("Exception occurred {}", e);
	    throw new ResourceNotFetchedException(String.format("Quote %s not fetched", symbol));
	}
    }

    @Override
    public List<HistoricalQuote> getQuoteHistory(Market market, String symbol, LocalDate from, LocalDate to,
	    Interval interval) {
	List<HistoricalQuote> history = new ArrayList<>();
	if (from.isBefore(to)) {
	    URIBuilder uriBuilder = new URIBuilder().setScheme(requestProperties.getScheme())
		    .setHost(requestProperties.getHost())
		    .setPath(requestProperties.getHistoricalQuotePath() + symbol + market.getExtension())
		    .addParameter("crumb", requestProperties.getCrumb()).addParameter("includeAdjustedClose", "true")
		    .addParameter("interval", interval.getTag())
		    .addParameter("period1", String.valueOf(MathUtility.localDateToEpochSecond(from)))
		    .addParameter("period2", String.valueOf(MathUtility.localDateToEpochSecond(to)));
	    try {
		URI uri = uriBuilder.build();
		log.debug("Request received to fetch quote history of {} from:{} to:{}", symbol, from, to);
		log.info("Dispatching request to {}", uri);
		YahooFinanceHistoricalResponseDTO response = builder.build().get().uri(uri)
			.header("Cookie", requestProperties.getCookie()).retrieve()
			.bodyToMono(YahooFinanceHistoricalResponseDTO.class).block();
		log.debug("Response received from YahooFinance for {} with WebFlux Impl", symbol);
		if (response != null) {
		    List<YahooFinanceHistoricalResponseInner2DTO> result = response.getChart().getResult();
		    if (result != null) {
			YahooFinanceHistoricalResponseInner2DTO intermediateJsonLevel1 = result.get(0);
			YahooFinanceHistoricalResponseInner6DTO intermediateJsonLevel2 = intermediateJsonLevel1
				.getIndicators();
			YahooFinanceHistoricalResponseInner7DTO opLoHiClAdjClMap = intermediateJsonLevel2.getQuote()
				.get(0);
			YahooFinanceHistoricalResponseInner8DTO adjCloseMap = intermediateJsonLevel2.getAdjclose()
				.get(0);
			if (intermediateJsonLevel1.getTimestamp() != null) {
			    List<Long> tradedDatesInObject = intermediateJsonLevel1.getTimestamp();
			    List<BigDecimal> opens = opLoHiClAdjClMap.getOpen();
			    List<BigDecimal> closes = opLoHiClAdjClMap.getClose();
			    List<BigDecimal> lows = opLoHiClAdjClMap.getLow();
			    List<BigDecimal> highs = opLoHiClAdjClMap.getHigh();
			    List<Long> volumes = intermediateJsonLevel2.getQuote().get(0).getVolume();
			    List<BigDecimal> adjCloses = adjCloseMap.getAdjclose();
			    for (int i = 0; i < tradedDatesInObject.size(); i++) {
				history.add(HistoricalQuote.builder()
					.tradedDate(MathUtility
						.epochSecondToLocalDate(tradedDatesInObject.get(i).longValue()))
					.open(opens.get(i)).close(closes.get(i)).low(lows.get(i)).high(highs.get(i))
					.adjustedClose(adjCloses.get(i)).volume(volumes.get(i)).build());
			    }
			    log.info("Mapping response to DTO completed for {}", symbol);
			}
		    }
		}
		return history;
	    } catch (URISyntaxException e) {
		log.warn("Exception occurred {}", e);
		throw new ResourceNotFetchedException(String.format("Quote %s not fetched", symbol));
	    }
	}
	log.warn("Quote {} is up to date", symbol);
	return history;
    }

}
