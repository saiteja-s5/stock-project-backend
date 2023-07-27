package building.stockapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

	@NotEmpty(message = "Stock Name field is Mandatory")
	@Column(name = "stock_name", length = 20, nullable = false)
	private String stockName;

	@NotNull(message = "Quantity field is Mandatory")
	@Min(value = 1, message = "Quantity should be atleast 1")
	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Embedded
	private Bought bought;

	@Embedded
	private Sold sold;

	@Override
	public String toString() {
		return profitLossId + "," + stockName + "," + bought + "," + sold;
	}

}
