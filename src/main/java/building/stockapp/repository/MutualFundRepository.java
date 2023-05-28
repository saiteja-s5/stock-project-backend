package building.stockapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import building.stockapp.model.MutualFund;

@Repository
public interface MutualFundRepository extends JpaRepository<MutualFund, Long> {

}
