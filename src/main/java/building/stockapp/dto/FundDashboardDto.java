package building.stockapp.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FundDashboardDto {

	private Double overallCashIn;
	private Double overallCreditedAmount;
	private Double overallDebitedAmount;
	private LocalDate fundLastTransactionOn;
	private LocalDate fundTableUpdatedOn;

}
