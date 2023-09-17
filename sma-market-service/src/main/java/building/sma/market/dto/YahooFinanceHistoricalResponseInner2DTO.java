package building.sma.market.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YahooFinanceHistoricalResponseInner2DTO {

    private YahooFinanceHistoricalResponseInner3DTO meta;
    private List<Long> timestamp;
    private YahooFinanceHistoricalResponseInner6DTO indicators;

}
