package building.stockapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import building.stockapp.dto.FundTableRowDTO;
import building.stockapp.model.Fund;

@Repository
public interface FundRepository extends JpaRepository<Fund, Long> {

	@Query("SELECT new building.stockapp.dto.FundTableRowDTO(f.transactionDate, f.creditedAmount, f.debitedAmount) FROM Fund f")
	List<FundTableRowDTO> getAllFundsForTable();

}
