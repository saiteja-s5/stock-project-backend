package building.stockapp.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundTableRowDto {

	private LocalDate transactionDate;
	private Double creditedAmount;
	private Double debitedAmount;
	private Double cashIn;

	public FundTableRowDto(LocalDate transactionDate, Double creditedAmount, Double debitedAmount) {
		super();
		this.transactionDate = transactionDate;
		this.creditedAmount = creditedAmount;
		this.debitedAmount = debitedAmount;
		this.cashIn = cashIn();
	}

	private Double cashIn() {
		return creditedAmount - debitedAmount;
	}

}
