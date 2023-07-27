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
@Table(name = "mutual_fund")
public class MutualFund {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "mutual_fund_id", unique = true)
	private Long mutualFundId;

	@NotNull(message = "Investment Date field is Mandatory")
	@PastOrPresent(message = "Investment Date cannot be furthur than today")
	@Column(name = "investment_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate investmentDate;

	@NotNull(message = "Amount Added field is Mandatory")
	@Digits(integer = 10, fraction = 2, message = "Amount Added accept's two decimal places")
	@DecimalMin(value = "0.01", message = "Amount Added should be atleast 0.01")
	@Column(name = "amount_added", nullable = false)
	private BigDecimal amountAdded;

	@NotEmpty(message = "Investment Type field is Mandatory")
	@Column(name = "investment_type", length = 10, nullable = false)
	private String investmentType;

	@NotNull(message = "Units Alloted field is Mandatory")
	@Digits(integer = 10, fraction = 4, message = "Units Alloted accept's four decimal places")
	@DecimalMin(value = "0.0001", message = "Units Alloted should be atleast 0.0001")
	@Column(name = "units_alloted", nullable = false)
	private BigDecimal unitsAlloted;

	@NotNull(message = "NAV field is Mandatory")
	@Digits(integer = 10, fraction = 4, message = "NAV accept's four decimal places")
	@DecimalMin(value = "0.0001", message = "NAV should be atleast 0.0001")
	@Column(name = "nav", nullable = false)
	private BigDecimal nav;

	public MutualFund(LocalDate investmentDate, BigDecimal amountAdded, String investmentType, BigDecimal unitsAlloted,
			BigDecimal nav) {
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
