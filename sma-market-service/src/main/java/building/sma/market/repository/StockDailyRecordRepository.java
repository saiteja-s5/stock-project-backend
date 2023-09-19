package building.sma.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import building.sma.market.model.StockDailyRecord;

public interface StockDailyRecordRepository extends JpaRepository<StockDailyRecord, Long> {

}
