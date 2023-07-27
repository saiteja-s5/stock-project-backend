package building.stockapp.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import building.stockapp.model.CompanyNameDropdown;
import building.stockapp.model.Excel;
import building.stockapp.service.CompanyNameDropdownService;
import building.stockapp.utility.ExcelToDatabase;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/company-dropdowns")
public class CompanyNameDropdownController {

	private CompanyNameDropdownService companyNameDropdownService;
	private static final Logger LOGGER = Logger.getLogger(CompanyNameDropdownController.class.getName());

	public CompanyNameDropdownController(CompanyNameDropdownService companyNameDropdownService) {
		this.companyNameDropdownService = companyNameDropdownService;
	}

	@PostMapping()
	public ResponseEntity<CompanyNameDropdown> addCompanyNameDropdown(
			@RequestBody @Valid CompanyNameDropdown companyNameDropdown) {
		LOGGER.log(Level.INFO, "Request received to add company in dropdown");
		return new ResponseEntity<>(companyNameDropdownService.addToDatabase(companyNameDropdown), HttpStatus.CREATED);
	}

	@PostMapping("/with-excel")
	public ResponseEntity<List<CompanyNameDropdown>> addCompanyNameDropdown(@RequestBody @Valid Excel excel) {
		LOGGER.log(Level.INFO, "Request received to add companies dropdowns with excel at path {0}",
				excel.getExcelPath());
		ExcelToDatabase ex2db = new ExcelToDatabase(excel);
		List<CompanyNameDropdown> companies = ex2db.readDataFromExcel();
		return new ResponseEntity<>(companyNameDropdownService.addToDatabase(companies), HttpStatus.CREATED);
	}

	@GetMapping()
	public ResponseEntity<List<CompanyNameDropdown>> getCompanyNameDropdowns() {
		LOGGER.log(Level.INFO, "Request received to get all added company dropdowns");
		return new ResponseEntity<>(companyNameDropdownService.getCompanyNameDropdowns(), HttpStatus.OK);
	}

}
