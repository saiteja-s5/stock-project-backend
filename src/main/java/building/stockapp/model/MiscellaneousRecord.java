package building.stockapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "miscellaneous_record")
public class MiscellaneousRecord {

	@Id
	@Column(name = "misc_id", unique = true)
	private String miscId;

	@NotNull(message = "Cash field is Mandatory")
	@Digits(integer = 10, fraction = 2, message = "Cash accept's two decimal places")
	@DecimalMin(value = "0.01", message = "Cash should be atleast 0.01")
	@Column(name = "cash_available", nullable = false)
	private BigDecimal cashAvailableForInvesting;

	@NotNull(message = "Stock Table Updated Date field is Mandatory")
	@PastOrPresent(message = "Stock Table Updated Date cannot be furthur than today")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "stock_updated_date", nullable = false)
	private LocalDate stockTableUpdatedOn;

	@NotNull(message = "Mutual Fund Table Updated Date field is Mandatory")
	@PastOrPresent(message = "Mutual Fund Table Updated Date cannot be furthur than today")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "mutual_fund_updated_date", nullable = false)
	private LocalDate mutualFundTableUpdatedOn;

	@NotNull(message = "Fund Table Updated Date field is Mandatory")
	@PastOrPresent(message = "Fund Table Updated Date cannot be furthur than today")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fund_updated_date", nullable = false)
	private LocalDate fundTableUpdatedOn;

	@NotNull(message = "Dividend Table Updated Date field is Mandatory")
	@PastOrPresent(message = "Dividend Table Updated Date cannot be furthur than today")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dividend_updated_date", nullable = false)
	private LocalDate dividendTableUpdatedOn;

	@NotNull(message = "Profit/Loss Table Updated Date field is Mandatory")
	@PastOrPresent(message = "Profit/Loss Table Updated Date cannot be furthur than today")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "profit_loss_updated_date", nullable = false)
	private LocalDate profitLossTableUpdatedOn;

	@NotNull(message = "Gold Table Updated Date field is Mandatory")
	@PastOrPresent(message = "Gold Table Updated Date cannot be furthur than today")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "gold_updated_date", nullable = false)
	private LocalDate goldTableUpdatedOn;

	public MiscellaneousRecord(BigDecimal cashAvailableForInvesting, LocalDate stockTableUpdatedOn,
			LocalDate mutualFundTableUpdatedOn, LocalDate fundTableUpdatedOn, LocalDate dividendTableUpdatedOn,
			LocalDate profitLossTableUpdatedOn, LocalDate goldTableUpdatedOn) {
		super();
		this.cashAvailableForInvesting = cashAvailableForInvesting;
		this.stockTableUpdatedOn = stockTableUpdatedOn;
		this.mutualFundTableUpdatedOn = mutualFundTableUpdatedOn;
		this.fundTableUpdatedOn = fundTableUpdatedOn;
		this.dividendTableUpdatedOn = dividendTableUpdatedOn;
		this.profitLossTableUpdatedOn = profitLossTableUpdatedOn;
		this.goldTableUpdatedOn = goldTableUpdatedOn;
	}

	@Override
	public String toString() {
		return miscId + "," + cashAvailableForInvesting + "," + stockTableUpdatedOn + "," + mutualFundTableUpdatedOn
				+ "," + fundTableUpdatedOn + "," + dividendTableUpdatedOn + "," + profitLossTableUpdatedOn + ","
				+ goldTableUpdatedOn;
	}

}
