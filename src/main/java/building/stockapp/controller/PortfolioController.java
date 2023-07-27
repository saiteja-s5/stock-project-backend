package building.stockapp.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import building.stockapp.model.Portfolio;
import building.stockapp.service.PortfolioService;

@CrossOrigin
@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

	private PortfolioService portfolioService;
	private static final Logger LOGGER = Logger.getLogger(PortfolioController.class.getName());

	public PortfolioController(PortfolioService portfolioService) {
		this.portfolioService = portfolioService;
	}

	@GetMapping()
	public ResponseEntity<List<Portfolio>> getPortfolioHistory() {
		LOGGER.log(Level.INFO, "Request received to get portfolio history");
		return new ResponseEntity<>(portfolioService.getPortfolioHistory(), HttpStatus.OK);
	}

}
