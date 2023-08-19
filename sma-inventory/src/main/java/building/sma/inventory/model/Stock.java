package building.sma.inventory.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stock_id")
    private Long stockId;

    @Column(name = "stock_name", length = 20)
    private String stockName;

    @Column(name = "investment_date")
    private LocalDate investmentDate;

    @Column(name = "quantity")
    private Integer quantity;

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
