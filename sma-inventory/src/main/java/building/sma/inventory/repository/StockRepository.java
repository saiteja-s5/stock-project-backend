package building.sma.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import building.sma.inventory.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

}
