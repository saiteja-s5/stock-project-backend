package building.sma.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import building.sma.market.model.CompanyName;

public interface CompanyNameRepository extends JpaRepository<CompanyName, Long> {

}
