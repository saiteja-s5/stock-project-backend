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
@Table(name = "mutual_fund")
public class MutualFund {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "mutual_fund_id", unique = true)
	private Long mutualFundId;

	@Column(name = "investment_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate investmentDate;

	@Column(name = "amount_added", nullable = false)
	private Double amountAdded;

	@Column(name = "investment_type", length = 10, nullable = false)
	private String investmentType;

	@Column(name = "units_alloted", nullable = false)
	private Double unitsAlloted;

	@Column(name = "nav", nullable = false)
	private Double nav;

	public MutualFund(LocalDate investmentDate, Double amountAdded, String investmentType, Double unitsAlloted,
			Double nav) {
		super();
		this.investmentDate = investmentDate;
		this.amountAdded = amountAdded;
		this.investmentType = investmentType;
		this.unitsAlloted = unitsAlloted;
		this.nav = nav;
	}

	@Override
	public String toString() {
		return mutualFundId + "," + investmentDate + "," + amountAdded + "," + investmentType + "," + unitsAlloted + ","
				+ nav;
	}

}
