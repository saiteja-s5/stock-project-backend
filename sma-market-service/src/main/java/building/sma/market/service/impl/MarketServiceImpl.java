package building.sma.market.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import building.sma.market.dto.YahooQuoteDTO;
import building.sma.market.exception.ResourceNotFetchedException;
import building.sma.market.model.HistoricalQuote;
import building.sma.market.model.Interval;
import building.sma.market.model.YahooFinanceApiRequestProperties;
import building.sma.market.service.MarketService;
import building.sma.market.utility.MathUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
@Service
@AllArgsConstructor
public class MarketServiceImpl implements MarketService {

    private final YahooFinanceApiRequestProperties requestProperties;

    @Override
    public YahooQuoteDTO getQuote(String market, String symbol) {
	String marketExtension = getMarketExtension(market);
	URIBuilder builder = new URIBuilder().setScheme(requestProperties.getScheme())
		.setHost(requestProperties.getHost()).setPath(requestProperties.getQuotePath())
		.addParameter("symbols", symbol + marketExtension).addParameter("crumb", requestProperties.getCrumb());
	try {
	    URI uri = builder.build();
	    log.info(String.format("Dispatching request to %s", uri));
	    HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request = HttpRequest.newBuilder(uri).GET().header("Cookie", requestProperties.getCookie())
		    .build();
	    JSONObject response = new JSONObject(client.send(request, HttpResponse.BodyHandlers.ofString()).body());
	    log.info(String.format("Response received from %s", uri));
	    String quoteAsString = response.getJSONObject("quoteResponse").getJSONArray("result").get(0).toString();
	    YahooQuoteDTO fetchedResponse = new ObjectMapper().readValue(quoteAsString, YahooQuoteDTO.class);
	    log.info("Response mapping to DTO is completed");
	    return fetchedResponse;
	} catch (InterruptedException ie) {
	    log.warn(String.format("Interrupted Exception occured %s", ie));
	    Thread.currentThread().interrupt();
	    throw new ResourceNotFetchedException(String.format("Quote %s fetching interrupted", symbol));
	} catch (JSONException | IOException | URISyntaxException e) {
	    log.warn(String.format("Exception occured %s", e));
	    throw new ResourceNotFetchedException(String.format("Quote %s not fetched", symbol));
	}
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<HistoricalQuote> getHistory(String market, String symbol, LocalDate from, LocalDate to,
	    Interval interval) {
	List<HistoricalQuote> history = new ArrayList<>();
	String marketExtension = getMarketExtension(market);
	URIBuilder builder = new URIBuilder().setScheme(requestProperties.getScheme())
		.setHost(requestProperties.getHost())
		.setPath(requestProperties.getHistoricalQuotePath() + symbol + marketExtension)
		.addParameter("crumb", requestProperties.getCrumb()).addParameter("includeAdjustedClose", "true")
		.addParameter("interval", interval.getTag())
		.addParameter("period1", String.valueOf(MathUtil.localDateToEpochSecond(from)))
		.addParameter("period2", String.valueOf(MathUtil.localDateToEpochSecond(to.plusDays(1))));
	try {
	    URI uri = builder.build();
	    log.info(String.format("Dispatching request to %s", uri));
	    HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request = HttpRequest.newBuilder(uri).GET().header("Cookie", requestProperties.getCookie())
		    .build();
	    JSONObject response = new JSONObject(client.send(request, HttpResponse.BodyHandlers.ofString()).body());
	    log.info(String.format("Response received from %s", uri));
	    JSONObject intermediateJsonLevel1 = response.getJSONObject("chart").getJSONArray("result").getJSONObject(0);
	    JSONObject intermediateJsonLevel2 = intermediateJsonLevel1.getJSONObject("indicators");
	    Map<String, List<BigDecimal>> opLoHiClAdjClMap = (Map<String, List<BigDecimal>>) intermediateJsonLevel2
		    .getJSONArray("quote").toList().get(0);
	    Map<String, List<BigDecimal>> adjCloseMap = (Map<String, List<BigDecimal>>) intermediateJsonLevel2
		    .getJSONArray("adjclose").toList().get(0);
	    List<Object> tradedDatesInObject = intermediateJsonLevel1.getJSONArray("timestamp").toList();
	    List<BigDecimal> opens = opLoHiClAdjClMap.get("open");
	    List<BigDecimal> closes = opLoHiClAdjClMap.get("close");
	    List<BigDecimal> lows = opLoHiClAdjClMap.get("low");
	    List<BigDecimal> highs = opLoHiClAdjClMap.get("high");
	    List<Integer> volumes = ((Map<String, List<Integer>>) (intermediateJsonLevel2.getJSONArray("quote").toList()
		    .get(0))).get("volume");
	    List<BigDecimal> adjCloses = adjCloseMap.get("adjclose");
	    log.info("Response mapped to List");
	    for (int i = 0; i < tradedDatesInObject.size(); i++) {
		history.add(HistoricalQuote.builder()
			.tradedDate(MathUtil.epochSecondToLocalDate((int) tradedDatesInObject.get(i)))
			.open(opens.get(i)).close(closes.get(i)).low(lows.get(i)).high(highs.get(i))
			.adjustedClose(adjCloses.get(i)).volume((int) volumes.get(i)).build());
	    }
	    log.info("Mapping from List to Dto list completed");
	    return history;
	} catch (InterruptedException ie) {
	    log.warn(String.format("Interrupted Exception occured %s", ie));
	    Thread.currentThread().interrupt();
	    throw new ResourceNotFetchedException(String.format("Quote %s fetching interrupted", symbol));
	} catch (JSONException | IOException | URISyntaxException e) {
	    log.warn(String.format("Exception occured %s", e));
	    throw new ResourceNotFetchedException(String.format("Quote %s not fetched", symbol));
	}
    }

    @Override
    public List<YahooQuoteDTO> getQuotes(String market, List<String> symbols) {
	log.info("Getting all stock quotes");
	List<YahooQuoteDTO> stocks = new ArrayList<>();
	symbols.forEach(stockSymbol -> stocks.add(getQuote(market, stockSymbol)));
	stocks.sort((q1, q2) -> q2.getRegularMarketChangePercent().compareTo(q1.getRegularMarketChangePercent()));
	log.info(String.format("Retrieved %d stock quotes sucessfully", stocks.size()));
	return stocks;
    }

    private String getMarketExtension(String market) {
	if (market.equalsIgnoreCase("nse")) {
	    return ".NS";
	}
	return market.equalsIgnoreCase("bse") ? ".BO" : "";
    }

}
