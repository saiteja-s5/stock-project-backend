package building.stockapp.service;

import java.util.List;

import building.stockapp.model.CompanyNameDropdown;

public interface CompanyNameDropdownService {

	List<CompanyNameDropdown> addToDatabase(List<CompanyNameDropdown> companies);

	CompanyNameDropdown addToDatabase(CompanyNameDropdown companyNameDropdown);

	List<CompanyNameDropdown> getCompanyNameDropdowns();

}
