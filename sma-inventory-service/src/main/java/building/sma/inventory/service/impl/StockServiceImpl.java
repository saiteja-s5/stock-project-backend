package building.sma.inventory.service.impl;

import org.springframework.stereotype.Service;

import building.sma.inventory.dto.StockPostRequestDTO;
import building.sma.inventory.dto.StockPostResponseDTO;
import building.sma.inventory.exception.ResourceNotAddedException;
import building.sma.inventory.model.Stock;
import building.sma.inventory.repository.StockRepository;
import building.sma.inventory.service.StockService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Override
    public StockPostResponseDTO postStock(StockPostRequestDTO stockPostRequestDTO) {
	Stock stock = Stock.builder().stockName(stockPostRequestDTO.getStockName())
		.investmentDate(stockPostRequestDTO.getInvestmentDate()).quantity(stockPostRequestDTO.getQuantity())
		.buyPrice(stockPostRequestDTO.getBuyPrice()).build();
	try {
	    Stock savedStock = stockRepository.save(stock);
	    return StockPostResponseDTO.builder().id(savedStock.getStockId()).stockName(savedStock.getStockName())
		    .build();
	} catch (Exception e) {
	    log.error(String.format("Stock:%s not posted, an exception was occured", stock.getStockName()), e);
	    throw new ResourceNotAddedException(String.format("Stock:%s not posted", stock.getStockName()));
	}
    }

}
