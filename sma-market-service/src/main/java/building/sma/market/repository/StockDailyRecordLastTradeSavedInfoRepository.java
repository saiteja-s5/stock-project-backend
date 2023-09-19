package building.sma.market.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import building.sma.market.model.Market;
import building.sma.market.model.StockDailyRecordLastTradeSavedInfo;

public interface StockDailyRecordLastTradeSavedInfoRepository
	extends JpaRepository<StockDailyRecordLastTradeSavedInfo, Long> {

    Optional<StockDailyRecordLastTradeSavedInfo> findByStockIdAndMarketId(String stockId, Market marketId);

}
