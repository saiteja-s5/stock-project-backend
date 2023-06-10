package building.stockapp.dto;

import java.time.LocalDate;
import java.time.Period;

import building.stockapp.utility.MathUtility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfitLossTableRowDto {

	private String stockName;
	private Integer quantity;
	private LocalDate buyDate;
	private Double buyPrice;
	private Double buyValue;
	private LocalDate sellDate;
	private Double sellPrice;
	private Double sellValue;
	private Period holdDuration;
	private Double profitOrLoss;
	private Double perReturn;
	private Double percentReturnperMonth;

	public ProfitLossTableRowDto(String stockName, Integer quantity, LocalDate buyDate, Double buyPrice,
			LocalDate sellDate, Double sellPrice) {
		super();
		this.stockName = stockName;
		this.quantity = quantity;
		this.buyDate = buyDate;
		this.buyPrice = buyPrice;
		this.buyValue = buyValue();
		this.sellDate = sellDate;
		this.sellPrice = sellPrice;
		this.sellValue = sellValue();
		this.holdDuration = holdDuration();
		this.profitOrLoss = profitOrLoss();
		this.perReturn = percentReturn();
		this.percentReturnperMonth = percentReturnPerMonth();
	}

	private Double buyValue() {
		return MathUtility.roundTo(buyPrice * quantity, 2);
	}

	private Double sellValue() {
		return MathUtility.roundTo(sellPrice * quantity, 2);
	}

	private Period holdDuration() {
		return Period.between(buyDate, sellDate);
	}

	private Double profitOrLoss() {
		return MathUtility.roundTo((sellPrice - buyPrice) * quantity, 2);
	}

	private Double percentReturn() {
		return MathUtility.percentageReturn(getBuyValue(), getSellValue());
	}

	private Double percentReturnPerMonth() {
		int months = getHoldDuration().getYears() * MathUtility.MONTHS_IN_YEAR + getHoldDuration().getMonths()
				+ (getHoldDuration().getDays() >= 0 ? 1 : 0);
		return MathUtility.roundTo((((sellPrice - buyPrice) / buyPrice) * 100) / months, 2);
	}

}
