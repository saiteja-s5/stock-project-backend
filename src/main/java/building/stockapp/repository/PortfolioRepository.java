package building.stockapp.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import building.stockapp.model.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, LocalDate> {

	Optional<Portfolio> findFirstByOrderByTradeDateDesc();

}
