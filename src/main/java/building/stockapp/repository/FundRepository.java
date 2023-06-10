package building.stockapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import building.stockapp.dto.FundTableRowDto;
import building.stockapp.model.Fund;

@Repository
public interface FundRepository extends JpaRepository<Fund, Long> {

	@Query("SELECT new building.stockapp.dto.FundTableRowDto(f.transactionDate, f.creditedAmount, f.debitedAmount) FROM Fund f")
	List<FundTableRowDto> getAllFundsForTable();

}
