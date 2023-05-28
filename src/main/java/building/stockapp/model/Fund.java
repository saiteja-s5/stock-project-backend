package building.stockapp.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

	@Column(name = "transaction_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate transactionDate;

	@Column(name = "credited_amount", nullable = false)
	private Double creditedAmount;

	@Column(name = "debited_amount", nullable = false)
	private Double debitedAmount;

	public Fund(LocalDate transactionDate, Double creditedAmount, Double debitedAmount) {
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
