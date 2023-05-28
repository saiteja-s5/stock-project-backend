package building.stockapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import building.stockapp.model.Dividend;

@Repository
public interface DividendRepository extends JpaRepository<Dividend, Long> {

}
