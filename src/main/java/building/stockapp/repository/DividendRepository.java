package building.stockapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import building.stockapp.dto.DividendTableRowDto;
import building.stockapp.model.Dividend;

@Repository
public interface DividendRepository extends JpaRepository<Dividend, Long> {

	@Query("SELECT new building.stockapp.dto.DividendTableRowDto(c.companyName, d.creditedDate, d.creditedAmount) FROM Dividend d JOIN CompanyNameDropdown c ON d.companyName = c.companySymbol")
	List<DividendTableRowDto> getAllDividendsForTable();

}
