package building.stockapp.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "miscellaneous_record")
public class MiscellaneousRecord {

	@Id
	@Column(name = "misc_id", unique = true)
	private String miscId;

	@Column(name = "cash_available", nullable = false)
	private Double cashAvailableForInvesting;

	@Column(name = "stock_updated_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate stockTableUpdatedOn;

	@Column(name = "mutual_fund_updated_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate mutualFundTableUpdatedOn;

	@Column(name = "fund_updated_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fundTableUpdatedOn;

	@Column(name = "dividend_updated_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dividendTableUpdatedOn;

	@Column(name = "profit_loss_updated_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate profitLossTableUpdatedOn;

	@Column(name = "gold_updated_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate goldTableUpdatedOn;

	public MiscellaneousRecord(Double cashAvailableForInvesting, LocalDate stockTableUpdatedOn,
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
