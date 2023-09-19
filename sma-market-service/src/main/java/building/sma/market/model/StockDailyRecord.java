package building.sma.market.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import building.sma.market.dto.HistoricalQuote;
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
@Table(name = "stock_daily_record")
public class StockDailyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "record_id")
    private Long recordId;

    @Enumerated(EnumType.STRING)
    @Column(name = "market_id", length = 20)
    private Market marketId;

    @Column(name = "stock_id", length = 20)
    private String stockId;

    @Column(name = "traded_date")
    private LocalDate tradedDate;

    @Column(name = "open")
    private BigDecimal open;

    @Column(name = "close")
    private BigDecimal close;

    @Column(name = "adj_close")
    private BigDecimal adjClose;

    @Column(name = "high")
    private BigDecimal high;

    @Column(name = "low")
    private BigDecimal low;

    @Column(name = "volume")
    private Long volume;

    public StockDailyRecord(HistoricalQuote quote, Market marketId, String stockId) {
	this.tradedDate = quote.getTradedDate();
	this.open = quote.getOpen();
	this.close = quote.getClose();
	this.adjClose = quote.getAdjustedClose();
	this.high = quote.getHigh();
	this.low = quote.getLow();
	this.volume = quote.getVolume();
	this.marketId = marketId;
	this.stockId = stockId;
    }

    @Override
    public String toString() {
	return recordId + "," + marketId + "," + stockId + "," + tradedDate + "," + open + "," + close + "," + adjClose
		+ "," + high + "," + low + "," + volume;
    }

}
