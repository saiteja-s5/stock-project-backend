package building.stockapp.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FundDashboardDto {

	private Double fundOverallCashIn;
	private Double fundOverallCreditedAmount;
	private Double fundOverallDebitedAmount;
	private LocalDate fundLastTransactionOn;
	private LocalDate fundTableLastUpdatedOn;

}
