package building.stockapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DividendTableRowDTO {

	private String companyName;
	private LocalDate creditedDate;
	private BigDecimal creditedAmount;

	public DividendTableRowDTO(String companyName, LocalDate creditedDate, BigDecimal creditedAmount) {
		super();
		this.companyName = companyName;
		this.creditedDate = creditedDate;
		this.creditedAmount = creditedAmount;
	}

}
