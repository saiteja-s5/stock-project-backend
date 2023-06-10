package building.stockapp.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DividendTableRowDto {

	private String companyName;
	private LocalDate creditedDate;
	private Double creditedAmount;

	public DividendTableRowDto(String companyName, LocalDate creditedDate, Double creditedAmount) {
		super();
		this.companyName = companyName;
		this.creditedDate = creditedDate;
		this.creditedAmount = creditedAmount;
	}

}
