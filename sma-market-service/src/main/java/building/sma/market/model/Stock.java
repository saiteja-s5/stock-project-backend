package building.sma.market.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "market", length = 20)
    private Market market;

    @Column(name = "stock_name", length = 20)
    private String stockName;

    @Column(name = "investment_date")
    private LocalDate investmentDate;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "buy_price")
    private BigDecimal buyPrice;

    public Stock(String stockName, Market market, LocalDate investmentDate, Integer quantity, BigDecimal buyPrice) {
	super();
	this.stockName = stockName;
	this.market = market;
	this.investmentDate = investmentDate;
	this.quantity = quantity;
	this.buyPrice = buyPrice;
    }

    @Override
    public String toString() {
	return stockId + "," + stockName + "," + market + "," + investmentDate + "," + quantity + "," + buyPrice;
    }

}
