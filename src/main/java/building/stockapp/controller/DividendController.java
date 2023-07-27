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

import building.stockapp.dto.DividendTableRowDTO;
import building.stockapp.model.Dividend;
import building.stockapp.service.DividendService;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/dividends")
public class DividendController {

	private DividendService dividendService;
	private static final Logger LOGGER = Logger.getLogger(DividendController.class.getName());

	public DividendController(DividendService dividendService) {
		this.dividendService = dividendService;
	}

	@PostMapping()
	public ResponseEntity<Dividend> addDividend(@RequestBody @Valid Dividend dividend) {
		LOGGER.log(Level.INFO, "Request received to add dividend");
		return new ResponseEntity<>(dividendService.addDividend(dividend), HttpStatus.CREATED);
	}

	@GetMapping()
	public ResponseEntity<List<DividendTableRowDTO>> getDividends() {
		LOGGER.log(Level.INFO, "Request received to get all added dividends");
		return new ResponseEntity<>(dividendService.getDividendsForTable(), HttpStatus.OK);
	}

}
