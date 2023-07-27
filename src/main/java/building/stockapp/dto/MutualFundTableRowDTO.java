package building.stockapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import building.stockapp.utility.Utility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MutualFundTableRowDTO {

	private LocalDate investmentDate;
	private BigDecimal amountAdded;
	private String investmentType;
	private BigDecimal unitsAlloted;
	private BigDecimal nav;
	private Period holdDuration;
	private BigDecimal overallReturnPercent;

	public MutualFundTableRowDTO(LocalDate investmentDate, BigDecimal amountAdded, String investmentType,
			BigDecimal unitsAlloted, BigDecimal nav) {
		super();
		this.investmentDate = investmentDate;
		this.amountAdded = amountAdded;
		this.investmentType = investmentType;
		this.unitsAlloted = unitsAlloted;
		this.nav = nav;
		this.holdDuration = holdDuration();
		this.overallReturnPercent = overallReturnPercent();
	}

	private Period holdDuration() {
		return Period.between(investmentDate, LocalDate.now());
	}

	private BigDecimal overallReturnPercent() {
		return Utility.roundTo(((0 - nav.doubleValue()) / nav.doubleValue()) * 100, 2);
	}

}
