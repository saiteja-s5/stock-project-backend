package building.sma.market.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YahooFinanceHistoricalResponseInner6DTO {

    private List<YahooFinanceHistoricalResponseInner7DTO> quote;
    private List<YahooFinanceHistoricalResponseInner8DTO> adjclose;

}
