package building.stockapp.dto;

import java.time.LocalDate;
import java.time.Period;

import building.stockapp.utility.Utility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MutualFundTableRowDto {

	private LocalDate investmentDate;
	private Double amountAdded;
	private String investmentType;
	private Double unitsAlloted;
	private Double nav;
	private Period holdDuration;
	private Double overallReturnPercent;

	public MutualFundTableRowDto(LocalDate investmentDate, Double amountAdded, String investmentType,
			Double unitsAlloted, Double nav) {
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

	private Double overallReturnPercent() {
		return Utility.roundTo(((0 - nav) / nav) * 100, 2);
	}

}
