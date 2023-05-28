package building.stockapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import building.stockapp.model.Fund;

@Repository
public interface FundRepository extends JpaRepository<Fund, Long> {

}
