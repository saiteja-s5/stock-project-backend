package building.sma.market.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StockDashboardDTO {

    private BigDecimal stockInvestmentValue;
    private BigDecimal stockCurrentValue;
    private BigDecimal stockCurrentReturn;
    private BigDecimal stockCurrentReturnPercent;
    private LocalDate stockLastTransactionOn;
    private LocalDate stockTableUpdatedOn;

}
