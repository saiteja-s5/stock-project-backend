package building.stockapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fund")
public class Fund {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "fund_id", unique = true)
	private Long fundId;

	@NotNull(message = "Transaction Date field is Mandatory")
	@PastOrPresent(message = "Transaction Date cannot be furthur than today")
	@Column(name = "transaction_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate transactionDate;

	@NotNull(message = "Credited Amount field is Mandatory")
	@Digits(integer = 10, fraction = 2, message = "Credited Amount accept's two decimal places")
	@DecimalMin(value = "0.01", message = "Credited Amount should be atleast 0.01")
	@Column(name = "credited_amount", nullable = false)
	private BigDecimal creditedAmount;

	@NotNull(message = "Debited Amount field is Mandatory")
	@Digits(integer = 10, fraction = 2, message = "Debited Amount accept's two decimal places")
	@DecimalMin(value = "0.01", message = "Debited Amount should be atleast 0.01")
	@Column(name = "debited_amount", nullable = false)
	private BigDecimal debitedAmount;

	public Fund(LocalDate transactionDate, BigDecimal creditedAmount, BigDecimal debitedAmount) {
		super();
		this.transactionDate = transactionDate;
		this.creditedAmount = creditedAmount;
		this.debitedAmount = debitedAmount;
	}

	@Override
	public String toString() {
		return fundId + "," + transactionDate + "," + creditedAmount + "," + debitedAmount;
	}

}
