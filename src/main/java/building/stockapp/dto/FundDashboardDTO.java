package building.stockapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FundDashboardDTO {

	private BigDecimal overallCashIn;
	private BigDecimal overallCreditedAmount;
	private BigDecimal overallDebitedAmount;
	private LocalDate fundLastTransactionOn;
	private LocalDate fundTableUpdatedOn;

}
