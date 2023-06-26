package building.stockapp.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfitLossDashboardDto {

	private Double profitLossEarnedOverall;
	private Double overallBoughtAmount;
	private Double overallSoldAmount;
	private Double averageReturnPerTransaction;
	private Double averageReturnPerTransactionPercent;
	private LocalDate profitLossLastTransactionOn;
	private LocalDate profitLossTableUpdatedOn;

}
