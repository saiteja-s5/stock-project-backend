package building.stockapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import building.stockapp.model.CompanyNameDropdown;

@Repository
public interface CompanyNameDropdownRepository extends JpaRepository<CompanyNameDropdown, String> {

	List<CompanyNameDropdown> findAllByOrderByCompanyNameAsc();

}
