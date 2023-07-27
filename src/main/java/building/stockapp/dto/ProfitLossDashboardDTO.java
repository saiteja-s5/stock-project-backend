package building.stockapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfitLossDashboardDTO {

	private BigDecimal profitLossEarnedOverall;
	private BigDecimal overallBoughtAmount;
	private BigDecimal overallSoldAmount;
	private BigDecimal overallProfitLossPercent;
	private BigDecimal averageReturnPerTransaction;
	private BigDecimal averageReturnPerTransactionPercent;
	private LocalDate profitLossLastTransactionOn;
	private LocalDate profitLossTableUpdatedOn;

}
