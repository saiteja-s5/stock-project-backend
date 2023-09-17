package building.sma.market.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YahooFinanceHistoricalResponseInner7DTO {

    private List<Long> volume;
    private List<BigDecimal> low;
    private List<BigDecimal> close;
    private List<BigDecimal> high;
    private List<BigDecimal> open;

}
