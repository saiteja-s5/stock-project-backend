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

import building.stockapp.dto.FundTableRowDto;
import building.stockapp.model.Fund;
import building.stockapp.service.FundService;

@CrossOrigin
@RestController
@RequestMapping("/funds")
public class FundController {

	private FundService fundService;
	private static final Logger LOGGER = Logger.getLogger(FundController.class.getName());

	public FundController(FundService fundService) {
		this.fundService = fundService;
	}

	@PostMapping()
	public ResponseEntity<Fund> addFund(@RequestBody Fund fund) {
		LOGGER.log(Level.INFO, "Request received to add fund");
		return new ResponseEntity<>(fundService.addFund(fund), HttpStatus.CREATED);
	}

	@GetMapping()
	public ResponseEntity<List<FundTableRowDto>> getFunds() {
		LOGGER.log(Level.INFO, "Request received to get all added funds");
		return new ResponseEntity<>(fundService.getFundsForTable(), HttpStatus.OK);
	}

}
