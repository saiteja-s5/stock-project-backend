package building.stockapp.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

	@Column(name = "stock_name", length = 20, nullable = false)
	private String stockName;

	@Column(name = "investment_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate investmentDate;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "buy_price", nullable = false)
	private Double buyPrice;

	public Stock(String stockName, LocalDate investmentDate, Integer quantity, Double buyPrice) {
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
