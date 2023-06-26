package building.stockapp.dto;

import java.time.LocalDate;
import java.time.Period;

import building.stockapp.utility.Utility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockTableRowDto {

	private String stockName;
	private Integer quantity;
	private LocalDate buyDate;
	private Double buyPrice;
	private Double buyValue;
	private Period holdDuration;
	private Double onePercentTarget;
	private Double twoPercentTarget;

	public StockTableRowDto(String stockName, Integer quantity, LocalDate buyDate, Double buyPrice) {
		super();
		this.stockName = stockName;
		this.quantity = quantity;
		this.buyDate = buyDate;
		this.buyPrice = buyPrice;
		this.buyValue = buyValue();
		this.holdDuration = holdDuration();
		this.onePercentTarget = onePercentTarget();
		this.twoPercentTarget = twoPercentTarget();
	}

	private Double buyValue() {
		return buyPrice * quantity;
	}

	private Period holdDuration() {
		return Period.between(buyDate, LocalDate.now());
	}

	private Double onePercentTarget() {
		return Utility.roundTo(Utility.getPercentTarget(1, holdDuration(), quantity, buyPrice), 2);
	}

	private Double twoPercentTarget() {
		return Utility.roundTo(Utility.getPercentTarget(2, holdDuration(), quantity, buyPrice), 2);
	}

}
