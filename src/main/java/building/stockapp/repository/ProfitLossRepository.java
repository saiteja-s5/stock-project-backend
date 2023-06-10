package building.stockapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import building.stockapp.dto.ProfitLossTableRowDto;
import building.stockapp.model.ProfitLoss;

@Repository
public interface ProfitLossRepository extends JpaRepository<ProfitLoss, Long> {

	@Query("SELECT new building.stockapp.dto.ProfitLossTableRowDto(d.companyName, p.quantity, bought.boughtDate, bought.boughtPrice, sold.soldDate, sold.soldPrice) FROM ProfitLoss p JOIN CompanyNameDropdown d ON p.stockName = d.companySymbol")
	List<ProfitLossTableRowDto> getAllProfitLossesForTable();

}
