package building.stockapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import building.stockapp.dto.StockTableRowDTO;
import building.stockapp.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

	@Query("SELECT new building.stockapp.dto.StockTableRowDTO(d.companyName, s.quantity, s.investmentDate, s.buyPrice) FROM Stock s JOIN CompanyNameDropdown d ON s.stockName = d.companySymbol")
	List<StockTableRowDTO> getAllStocksForTable();

}
