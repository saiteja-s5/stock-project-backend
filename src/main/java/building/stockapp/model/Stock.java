package building.stockapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock")
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "stock_id")
	private Long stockId;

	@NotEmpty(message = "{mandatory}")
	@Column(name = "stock_name", length = 20)
	private String stockName;

	@NotNull(message = "{mandatory}")
	@PastOrPresent(message = "{future}")
	@Column(name = "investment_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate investmentDate;

	@NotNull(message = "{mandatory}")
	@Min(value = 1, message = "{numeric-minimum}")
	@Column(name = "quantity")
	private Integer quantity;

	@NotNull(message = "{mandatory}")
	@Digits(integer = 10, fraction = 2, message = "{decimal-places}")
	@DecimalMin(value = "0.01", message = "{decimal-minimum-2-digits}")
	@Column(name = "buy_price")
	private BigDecimal buyPrice;

	public Stock(String stockName, LocalDate investmentDate, Integer quantity, BigDecimal buyPrice) {
		super();
		this.stockName = stockName;
		this.investmentDate = investmentDate;
		this.quantity = quantity;
		this.buyPrice = buyPrice;
	}

	@Override
	public String toString() {
		return stockId + "," + stockName + "," + investmentDate + "," + quantity + "," + buyPrice;
	}

}
