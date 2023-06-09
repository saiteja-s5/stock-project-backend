package building.stockapp.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import building.stockapp.dto.StockTableRowDto;
import building.stockapp.model.Stock;
import building.stockapp.service.StockService;

@CrossOrigin
@RestController
@RequestMapping("/stocks")
public class StockController {

	private StockService stockService;
	private static final Logger LOGGER = Logger.getLogger(StockController.class.getName());

	public StockController(StockService stockService) {
		this.stockService = stockService;
	}

	@PostMapping()
	public ResponseEntity<Stock> addStock(@RequestBody Stock stock) {
		LOGGER.log(Level.INFO, "Request received to add stock {0}", stock.getStockName());
		return new ResponseEntity<>(stockService.addStock(stock), HttpStatus.CREATED);
	}

	@GetMapping("/table")
	public ResponseEntity<List<StockTableRowDto>> getStocksForTable() {
		LOGGER.log(Level.INFO, "Request received to get all added stocks for populating in table");
		return new ResponseEntity<>(stockService.getStocksForTable(), HttpStatus.OK);
	}

	@GetMapping("/{stockId}")
	public ResponseEntity<Stock> getStockById(@PathVariable Long stockId) {
		LOGGER.log(Level.INFO, "Request received to get stock with Stock ID {0}", stockId);
		return new ResponseEntity<>(stockService.getStockById(stockId), HttpStatus.OK);
	}

	@DeleteMapping("/{stockId}")
	public ResponseEntity<Void> deleteStock(@PathVariable Long stockId) {
		LOGGER.log(Level.INFO, "Request received to delete stock with Stock ID {0}", stockId);
		stockService.deleteStock(stockId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
