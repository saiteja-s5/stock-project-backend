package building.sma.market.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YahooFinanceHistoricalResponseInner3DTO {

    private String currency;
    private String symbol;
    private String exchangeName;
    private String instrumentType;
    private Long firstTradeDate;
    private Long regularMarketTime;
    private Long gmtoffset;
    private String timezone;
    private String exchangeTimezoneName;
    private BigDecimal regularMarketPrice;
    private BigDecimal chartPreviousClose;
    private BigDecimal priceHint;
    private YahooFinanceHistoricalResponseInner4DTO currentTradingPeriod;
    private String dataGranularity;
    private String range;
    private List<String> validRanges;
}
