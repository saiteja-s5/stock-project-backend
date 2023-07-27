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
	@Column(name = "stock_id", unique = true)
	private Long stockId;

	@NotEmpty(message = "Stock Name field is Mandatory")
	@Column(name = "stock_name", length = 20, nullable = false)
	private String stockName;

	@NotNull(message = "Investment Date field is Mandatory")
	@PastOrPresent(message = "Investment Date cannot be furthur than today")
	@Column(name = "investment_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate investmentDate;

	@NotNull(message = "Quantity field is Mandatory")
	@Min(value = 1, message = "Buy Quantity should be atleast 1")
	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@NotNull(message = "Buy Price field is Mandatory")
	@Digits(integer = 10, fraction = 2, message = "Buy Price accept's two decimal places")
	@DecimalMin(value = "0.01", message = "Buy Price should be atleast 0.01")
	@Column(name = "buy_price", nullable = false)
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
