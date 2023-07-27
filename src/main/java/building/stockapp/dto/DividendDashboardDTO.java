package building.stockapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DividendDashboardDTO {

	private BigDecimal dividendEarnedOverall;
	private LocalDate dividendLastTransactionOn;
	private LocalDate dividendTableUpdatedOn;

}
