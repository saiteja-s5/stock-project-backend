package building.stockapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import building.stockapp.utility.Utility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfitLossTableRowDTO {

	private String stockName;
	private Integer quantity;
	private LocalDate buyDate;
	private BigDecimal buyPrice;
	private BigDecimal buyValue;
	private LocalDate sellDate;
	private BigDecimal sellPrice;
	private BigDecimal sellValue;
	private Period holdDuration;
	private BigDecimal profitLoss;
	private BigDecimal percentReturn;
	private BigDecimal percentReturnPerMonth;

	public ProfitLossTableRowDTO(String stockName, Integer quantity, LocalDate buyDate, BigDecimal buyPrice,
			LocalDate sellDate, BigDecimal sellPrice) {
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
		this.profitLoss = profitOrLoss();
		this.percentReturn = percentReturn();
		this.percentReturnPerMonth = percentReturnPerMonth();
	}

	private BigDecimal buyValue() {
		return Utility.roundTo(buyPrice.doubleValue() * quantity, 2);
	}

	private BigDecimal sellValue() {
		return Utility.roundTo(sellPrice.doubleValue() * quantity, 2);
	}

	private Period holdDuration() {
		return Period.between(buyDate, sellDate);
	}

	private BigDecimal profitOrLoss() {
		return Utility.roundTo((sellPrice.doubleValue() - buyPrice.doubleValue()) * quantity, 2);
	}

	private BigDecimal percentReturn() {
		return Utility.percentageReturn(getBuyValue().doubleValue(), getSellValue().doubleValue());
	}

	private BigDecimal percentReturnPerMonth() {
		int months = getHoldDuration().getYears() * Utility.MONTHS_IN_YEAR + getHoldDuration().getMonths()
				+ (getHoldDuration().getDays() >= 0 ? 1 : 0);
		return Utility.roundTo(
				(((sellPrice.doubleValue() - buyPrice.doubleValue()) / buyPrice.doubleValue()) * 100) / months, 2);
	}

}
