package building.stockapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

	@Embedded
	private Bought bought;

	@Embedded
	private Sold sold;

	@Override
	public String toString() {
		return profitLossId + "," + stockName + "," + bought + "," + sold;
	}

}
