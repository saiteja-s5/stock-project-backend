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

import building.stockapp.model.MutualFund;
import building.stockapp.service.MutualFundService;

@CrossOrigin
@RestController
@RequestMapping("/mutual-funds")
public class MutualFundController {

	private MutualFundService mutualFundService;
	private static final Logger LOGGER = Logger.getLogger(MutualFundController.class.getName());

	public MutualFundController(MutualFundService mutualFundService) {
		this.mutualFundService = mutualFundService;
	}

	@PostMapping("/")
	public ResponseEntity<MutualFund> addMutualFund(@RequestBody MutualFund mutualFund) {
		LOGGER.log(Level.INFO, "Request received to add mutual fund {0}", mutualFund);
		return new ResponseEntity<>(mutualFundService.addMutualFund(mutualFund), HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<MutualFund>> getMutualFunds() {
		LOGGER.log(Level.INFO, "Request received to get all added mutual funds");
		return new ResponseEntity<>(mutualFundService.getMutualFunds(), HttpStatus.OK);
	}

}
