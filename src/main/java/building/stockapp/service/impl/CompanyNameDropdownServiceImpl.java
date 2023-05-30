package building.stockapp.service.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import building.stockapp.model.CompanyNameDropdown;
import building.stockapp.repository.CompanyNameDropdownRepository;
import building.stockapp.service.CompanyNameDropdownService;

@Service
public class CompanyNameDropdownServiceImpl implements CompanyNameDropdownService {

	private CompanyNameDropdownRepository companyNameDropdownRepository;
	private static final Logger LOGGER = Logger.getLogger(CompanyNameDropdownServiceImpl.class.getName());

	public CompanyNameDropdownServiceImpl(CompanyNameDropdownRepository companyNameDropdownRepository) {
		this.companyNameDropdownRepository = companyNameDropdownRepository;
	}

	@Override
	public List<CompanyNameDropdown> addToDatabase(List<CompanyNameDropdown> companies) {
		LOGGER.log(Level.INFO, "Adding companies");
		List<CompanyNameDropdown> savedDropdowns = companyNameDropdownRepository.saveAll(companies);
		LOGGER.log(Level.INFO, "Companies added sucessfully");
		return savedDropdowns;
	}

	@Override
	public CompanyNameDropdown addToDatabase(CompanyNameDropdown companyNameDropdown) {
		LOGGER.log(Level.INFO, "Adding company");
		CompanyNameDropdown savedDropdown = companyNameDropdownRepository.save(companyNameDropdown);
		LOGGER.log(Level.INFO, "Company added sucessfully");
		return savedDropdown;
	}

	@Override
	public List<CompanyNameDropdown> getCompanyNameDropdowns() {
		LOGGER.log(Level.INFO, "Getting all added company dropdowns");
		List<CompanyNameDropdown> savedCompanyNameDropdowns = companyNameDropdownRepository
				.findAllByOrderByCompanyNameAsc();
		LOGGER.log(Level.INFO, "Retrieved {0} company dropdowns sucessfully", savedCompanyNameDropdowns.size());
		return savedCompanyNameDropdowns;
	}

}
