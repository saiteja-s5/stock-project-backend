package building.stockapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import building.stockapp.utility.Utility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockTableRowDTO {

	private String stockName;
	private Integer quantity;
	private LocalDate buyDate;
	private BigDecimal buyPrice;
	private BigDecimal buyValue;
	private Period holdDuration;
	private BigDecimal onePercentTarget;
	private BigDecimal twoPercentTarget;

	public StockTableRowDTO(String stockName, Integer quantity, LocalDate buyDate, BigDecimal buyPrice) {
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

	private BigDecimal buyValue() {
		return BigDecimal.valueOf(buyPrice.doubleValue() * quantity);
	}

	private Period holdDuration() {
		return Period.between(buyDate, LocalDate.now());
	}

	private BigDecimal onePercentTarget() {
		return Utility.roundTo(
				Utility.getPercentTarget(1.0, holdDuration(), quantity, buyPrice.doubleValue()).doubleValue(), 2);
	}

	private BigDecimal twoPercentTarget() {
		return Utility.roundTo(
				Utility.getPercentTarget(2.0, holdDuration(), quantity, buyPrice.doubleValue()).doubleValue(), 2);
	}

}
