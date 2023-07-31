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

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/stocks")
public class StockController {

	private final StockService stockService;

	@PostMapping()
	public ResponseEntity<Long> postStock(@RequestBody @Valid Stock stock) {
		return new ResponseEntity<>(stockService.postStock(stock), HttpStatus.CREATED);
	}

	@GetMapping("/{stockId}")
	public ResponseEntity<Stock> getStockById(@PathVariable Long stockId) {
		return new ResponseEntity<>(stockService.getStockById(stockId), HttpStatus.OK);
	}

	@GetMapping("/table")
	public ResponseEntity<List<StockTableRowDTO>> getStocksForTable() {
		return new ResponseEntity<>(stockService.getStocksForTable(), HttpStatus.OK);
	}

	@DeleteMapping("/{stockId}")
	public ResponseEntity<Void> deleteStock(@PathVariable Long stockId) {
		stockService.deleteStock(stockId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
