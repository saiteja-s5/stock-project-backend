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

import building.stockapp.model.ProfitLoss;
import building.stockapp.service.ProfitLossService;

@CrossOrigin
@RestController
@RequestMapping("/profit-losses")
public class ProfitLossController {

	private ProfitLossService profitLossService;
	private static final Logger LOGGER = Logger.getLogger(ProfitLossController.class.getName());

	public ProfitLossController(ProfitLossService profitLossService) {
		this.profitLossService = profitLossService;
	}

	@PostMapping("/")
	public ResponseEntity<ProfitLoss> addProfitLoss(@RequestBody ProfitLoss profitLoss) {
		LOGGER.log(Level.INFO, "Request received to add Profit/Loss");
		return new ResponseEntity<>(profitLossService.addProfitLoss(profitLoss), HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<ProfitLoss>> getProfitLoss() {
		LOGGER.log(Level.INFO, "Request received to get all added Profit/Loss entries");
		return new ResponseEntity<>(profitLossService.getProfitLoss(), HttpStatus.OK);
	}

}
