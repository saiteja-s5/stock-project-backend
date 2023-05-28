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
@Table(name = "dividend")
public class Dividend {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "dividend_id", unique = true)
	private Long dividendId;

	@Column(name = "company_name", length = 20, nullable = false)
	private String companyName;

	@Column(name = "credited_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate creditedDate;

	@Column(name = "credited_amount", nullable = false)
	private Double creditedAmount;

	public Dividend(String companyName, LocalDate creditedDate, Double creditedAmount) {
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
