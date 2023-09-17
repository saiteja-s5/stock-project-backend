package building.sma.market.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YahooFinanceHistoricalResponseInner1DTO {

    private List<YahooFinanceHistoricalResponseInner2DTO> result;
    private Object error;

}
