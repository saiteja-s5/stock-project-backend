package building.stockapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundTableRowDTO {

	private LocalDate transactionDate;
	private BigDecimal creditedAmount;
	private BigDecimal debitedAmount;
	private BigDecimal cashIn;

	public FundTableRowDTO(LocalDate transactionDate, BigDecimal creditedAmount, BigDecimal debitedAmount) {
		super();
		this.transactionDate = transactionDate;
		this.creditedAmount = creditedAmount;
		this.debitedAmount = debitedAmount;
		this.cashIn = cashIn();
	}

	private BigDecimal cashIn() {
		return BigDecimal.valueOf(creditedAmount.doubleValue() - debitedAmount.doubleValue());
	}

	@Override
	public String toString() {
		return transactionDate + "," + creditedAmount + "," + debitedAmount + "," + cashIn;
	}

}
