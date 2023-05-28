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
@Table(name = "profit_loss")
public class ProfitLoss {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "profit_loss_id", unique = true)
	private Long profitLossId;

	@Column(name = "stock_name", length = 20, nullable = false)
	private String stockName;

	@Column(name = "bought_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate boughtDate;

	@Column(name = "bought_price", nullable = false)
	private Double boughtPrice;

	@Column(name = "sold_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate soldDate;

	@Column(name = "sold_price", nullable = false)
	private Double soldPrice;

	public ProfitLoss(String stockName, LocalDate boughtDate, Double boughtPrice, LocalDate soldDate,
			Double soldPrice) {
		super();
		this.stockName = stockName;
		this.boughtDate = boughtDate;
		this.boughtPrice = boughtPrice;
		this.soldDate = soldDate;
		this.soldPrice = soldPrice;
	}

	@Override
	public String toString() {
		return profitLossId + "," + stockName + "," + boughtDate + "," + boughtPrice + "," + soldDate + "," + soldPrice;
	}

}
