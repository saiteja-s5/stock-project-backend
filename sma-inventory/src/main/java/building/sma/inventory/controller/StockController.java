package building.sma.inventory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import building.sma.inventory.dto.StockPostRequestDTO;
import building.sma.inventory.dto.StockPostResponseDTO;
import building.sma.inventory.service.StockService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    @PostMapping
    public ResponseEntity<StockPostResponseDTO> postStock(@RequestBody @Valid StockPostRequestDTO stock) {
	return new ResponseEntity<>(stockService.postStock(stock), HttpStatus.CREATED);
    }

}
