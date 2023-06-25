package building.stockapp.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfitLossDashboardDto {

	private Double plOverallAmount;
	private Double plOverallBoughtAmount;
	private Double plOverallSoldAmount;
	private Double plAverageReturnPerTransaction;
	private Double plAverageReturnPerTransactionPercent;
	private LocalDate plLastTransactionOn;
	private LocalDate plTableLastUpdatedOn;

}
