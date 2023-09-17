package building.sma.market.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YahooFinanceHistoricalResponseInner4DTO {

    private YahooFinanceHistoricalResponseInner5DTO pre;
    private YahooFinanceHistoricalResponseInner5DTO regular;
    private YahooFinanceHistoricalResponseInner5DTO post;

}
