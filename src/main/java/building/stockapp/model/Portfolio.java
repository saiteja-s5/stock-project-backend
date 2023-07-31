package building.stockapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "portfolio")
public class Portfolio {

	@Id
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "trade_date", nullable = false)
	private LocalDate tradeDate;

	@NotNull(message = "Portfolio value field is Mandatory")
	@Digits(integer = 10, fraction = 2, message = "Portfolio value accept's two decimal places")
	@Column(name = "portfolio_value", nullable = false)
	private BigDecimal portfolioValue;

	@NotNull(message = "Market change field is Mandatory")
	@Digits(integer = 10, fraction = 2, message = "Market change accept's two decimal places")
	@Column(name = "market_change", nullable = false)
	private BigDecimal marketChange;

	@NotNull(message = "Market change percent field is Mandatory")
	@Digits(integer = 10, fraction = 2, message = "Market change percent accept's two decimal places")
	@Column(name = "market_change_percent", nullable = false)
	private BigDecimal marketChangePercent;

	@Override
	public String toString() {
		return tradeDate + "," + portfolioValue + "," + marketChange + "," + marketChangePercent;
	}

}
