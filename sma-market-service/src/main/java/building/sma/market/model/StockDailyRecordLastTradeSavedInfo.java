package building.sma.market.model;

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
@Table(name = "stock_daily_record_last_saved")
public class StockDailyRecordLastTradeSavedInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "last_saved_id")
    private Long lastSavedId;

    @Enumerated(EnumType.STRING)
    @Column(name = "market_id", length = 20)
    private Market marketId;

    @Column(name = "stock_id", length = 20)
    private String stockId;

    @Column(name = "last_saved_date")
    private LocalDate lastSavedDate;

    public StockDailyRecordLastTradeSavedInfo(Market marketId, String stockId, LocalDate lastSavedDate) {
	this.marketId = marketId;
	this.stockId = stockId;
	this.lastSavedDate = lastSavedDate;
    }

}
