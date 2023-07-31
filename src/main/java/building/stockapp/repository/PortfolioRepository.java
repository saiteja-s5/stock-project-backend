package building.stockapp.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import building.stockapp.model.Portfolio;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, LocalDate> {

	Optional<Portfolio> findFirstByOrderByTradeDateDesc();

}
