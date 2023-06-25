package building.stockapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YahooStockQuoteDto {

	private String language;
	private String region;
	private String quoteType;
	private String typeDisp;
	private String quoteSourceName;
	private Boolean triggerable;
	private String customPriceAlertConfidence;
	private String currency;
	private String marketState;
	private String exchangeTimezoneName;
	private String exchangeTimezoneShortName;
	private Double gmtOffSetMilliseconds;
	private String market;
	private Boolean esgPopulated;
	private Double regularMarketChangePercent;
	private Double regularMarketPrice;
	private String exchange;
	private String shortName;
	private String longName;
	private String messageBoardId;
	private Double firstTradeDateMilliseconds;
	private Double priceHint;
	private Double regularMarketChange;
	private Double regularMarketTime;
	private Double regularMarketDayHigh;
	private String regularMarketDayRange;
	private Double regularMarketDayLow;
	private Double regularMarketVolume;
	private Boolean tradeable;
	private Boolean cryptoTradeable;
	private Double regularMarketPreviousClose;
	private Double bid;
	private Double ask;
	private Double bidSize;
	private Double askSize;
	private String prevName;
	private String nameChangeDate;
	private String underlyingSymbol;
	private String fullExchangeName;
	private String financialCurrency;
	private Double regularMarketOpen;
	private Double averageDailyVolume3Month;
	private Double averageDailyVolume10Day;
	private Double fiftyTwoWeekLowChange;
	private Double fiftyTwoWeekLowChangePercent;
	private String fiftyTwoWeekRange;
	private Double fiftyTwoWeekHighChange;
	private Double fiftyTwoWeekHighChangePercent;
	private Double fiftyTwoWeekLow;
	private Double fiftyTwoWeekHigh;
	private Double earningsTimestamp;
	private Double earningsTimestampStart;
	private Double earningsTimestampEnd;
	private Double trailingAnnualDividendRate;
	private Double trailingPE;
	private Double dividendRate;
	private Double trailingAnnualDividendYield;
	private Double dividendYield;
	private Double epsTrailingTwelveMonths;
	private Double epsForward;
	private Double epsCurrentYear;
	private Double priceEpsCurrentYear;
	private Double sharesOutstanding;
	private Double bookValue;
	private Double fiftyDayAverage;
	private Double fiftyDayAverageChange;
	private Double fiftyDayAverageChangePercent;
	private Double twoHundredDayAverage;
	private Double twoHundredDayAverageChange;
	private Double twoHundredDayAverageChangePercent;
	private Double marketCap;
	private Double forwardPE;
	private Double priceToBook;
	private Double sourceInterval;
	private Double exchangeDataDelayedBy;
	private String averageAnalystRating;
	private String symbol;

}
