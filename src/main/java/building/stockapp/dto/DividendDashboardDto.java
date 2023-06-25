package building.stockapp.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DividendDashboardDto {

	private Double dividendOverallEarned;
	private LocalDate dividendLastTransactionOn;
	private LocalDate dividendTableLastUpdatedOn;

}
