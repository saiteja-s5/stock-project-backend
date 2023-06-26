package building.stockapp.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StockDashboardDto {

	private Double stockInvestmentValue;
	private Double stockCurrentValue;
	private Double stockCurrentReturn;
	private Double stockCurrentReturnPercent;
	private LocalDate stockLastTransactionOn;
	private LocalDate stockTableUpdatedOn;

}
