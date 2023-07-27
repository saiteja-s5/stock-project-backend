package building.stockapp.controller;

import java.util.List;

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

import building.stockapp.dto.StockTableRowDTO;
import building.stockapp.model.Stock;
import building.stockapp.service.StockService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/stocks")
public class StockController {

	private final StockService stockService;

	@PostMapping()
	public ResponseEntity<Long> addStock(@RequestBody @Valid Stock stock) {
		log.info("Request received to add stock:{0}", stock.getStockName());
		return new ResponseEntity<>(stockService.addStock(stock), HttpStatus.CREATED);
	}

	@GetMapping("/{stockId}")
	public ResponseEntity<Stock> getStockById(@PathVariable Long stockId) {
		log.info("Request received to get Stock with Id:{0}", stockId);
		return new ResponseEntity<>(stockService.getStockById(stockId), HttpStatus.OK);
	}

	@GetMapping("/table")
	public ResponseEntity<List<StockTableRowDTO>> getStocksForTable() {
		log.info("Request received to get all added stocks for table");
		return new ResponseEntity<>(stockService.getStocksForTable(), HttpStatus.OK);
	}

	@DeleteMapping("/{stockId}")
	public ResponseEntity<Void> deleteStock(@PathVariable Long stockId) {
		log.info("Request received to delete Stock with Id:{0}", stockId);
		stockService.deleteStock(stockId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
