package building.stockapp.dto;

import java.time.LocalDate;
import java.time.Period;

import building.stockapp.utility.MathUtility;
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
	private Double onePerTarget;
	private Double twoPerTarget;

	public StockTableRowDto(String stockName, Integer quantity, LocalDate buyDate, Double buyPrice) {
		super();
		this.stockName = stockName;
		this.quantity = quantity;
		this.buyDate = buyDate;
		this.buyPrice = buyPrice;
		this.buyValue = buyValue();
		this.holdDuration = holdDuration();
		this.onePerTarget = onePerTarget();
		this.twoPerTarget = twoPerTarget();
	}

	public Double buyValue() {
		return buyPrice * quantity;
	}

	public Period holdDuration() {
		return Period.between(buyDate, LocalDate.now());
	}

	public Double onePerTarget() {
		return MathUtility.roundTo(MathUtility.getPercentTarget(1, holdDuration(), quantity, buyPrice), 2);
	}

	public Double twoPerTarget() {
		return MathUtility.roundTo(MathUtility.getPercentTarget(2, holdDuration(), quantity, buyPrice), 2);
	}

}
