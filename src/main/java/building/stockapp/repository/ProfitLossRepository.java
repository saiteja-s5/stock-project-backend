package building.stockapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import building.stockapp.model.ProfitLoss;

@Repository
public interface ProfitLossRepository extends JpaRepository<ProfitLoss, Long> {

}
