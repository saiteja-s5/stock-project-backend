package building.stockapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import building.stockapp.dto.MutualFundTableRowDto;
import building.stockapp.model.MutualFund;

@Repository
public interface MutualFundRepository extends JpaRepository<MutualFund, Long> {

	@Query("SELECT new building.stockapp.dto.MutualFundTableRowDto(m.investmentDate, m.amountAdded, m.investmentType, m.unitsAlloted, m.nav) FROM MutualFund m")
	List<MutualFundTableRowDto> getAllMutualFundsForTable();

}
