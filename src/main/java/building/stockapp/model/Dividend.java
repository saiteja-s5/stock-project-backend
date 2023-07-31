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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dividend")
public class Dividend {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "dividend_id", unique = true)
	private Long dividendId;

	@NotEmpty(message = "Company Name field is Mandatory")
	@Column(name = "company_name", length = 20, nullable = false)
	private String companyName;

	@NotNull(message = "Credited Date field is Mandatory")
	@PastOrPresent(message = "Credited Date cannot be furthur than today")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "credited_date", nullable = false)
	private LocalDate creditedDate;

	@NotNull(message = "Credited Amount field is Mandatory")
	@Digits(integer = 10, fraction = 2, message = "Credited Amount accept's two decimal places")
	@DecimalMin(value = "0.01", message = "Credited Amount should be atleast 0.01")
	@Column(name = "credited_amount", nullable = false)
	private BigDecimal creditedAmount;

	public Dividend(String companyName, LocalDate creditedDate, BigDecimal creditedAmount) {
		super();
		this.companyName = companyName;
		this.creditedDate = creditedDate;
		this.creditedAmount = creditedAmount;
	}

	@Override
	public String toString() {
		return dividendId + "," + companyName + "," + creditedDate + "," + creditedAmount;
	}

}
