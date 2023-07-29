package building.stockapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import building.stockapp.dto.StockTableRowDTO;
import building.stockapp.exception.ResourceNotAddedException;
import building.stockapp.exception.ResourceNotDeletedException;
import building.stockapp.exception.ResourceNotFoundException;
import building.stockapp.exception.TableFetchException;
import building.stockapp.model.Stock;
import building.stockapp.repository.StockRepository;
import building.stockapp.service.StockService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@Service
public class StockServiceImpl implements StockService {

	private final StockRepository stockRepository;

	private static final String ERROR_MESSAGE = "Stock table is empty";

	@Override
	public Long postStock(Stock stock) {
		Stock savedStock;
		try {
			savedStock = stockRepository.save(stock);
			return savedStock.getStockId();
		} catch (Exception e) {
			log.error(String.format("Stock:%s not posted, an exception was occured", stock.getStockName()), e);
			throw new ResourceNotAddedException(String.format("Stock:%s not posted", stock.getStockName()));
		}
	}

	@Override
	public Stock getStockById(Long stockId) {
		try {
			return stockRepository.findById(stockId).orElseThrow(
					() -> new ResourceNotFoundException(String.format("Stock with Id:%d not found", stockId)));
		} catch (ResourceNotFoundException rnfe) {
			log.error(String.format("Stock with Id:%d not found, an exception occured", stockId), rnfe);
			throw new ResourceNotFoundException(rnfe.getMessage());
		}
	}

	@Override
	public List<Stock> getStocks() {
		try {
			List<Stock> savedStocks = stockRepository.findAll();
			if (savedStocks.isEmpty()) {
				log.warn(ERROR_MESSAGE);
				return new ArrayList<>();
			}
			return savedStocks;
		} catch (Exception e) {
			log.error("Unable to fetch requested stocks, an exception occured", e);
			throw new TableFetchException("Unable to fetch requested stocks");
		}
	}

	@Override
	public List<StockTableRowDTO> getStocksForTable() {
		try {
			List<StockTableRowDTO> savedStocks = stockRepository.getAllStocksForTable();
			if (savedStocks.isEmpty()) {
				log.warn(ERROR_MESSAGE);
				return new ArrayList<>();
			}
			return savedStocks;
		} catch (Exception e) {
			log.error("Unable to fetch requested stocks for table, an exception occured", e);
			throw new TableFetchException("Unable to fetch requested stocks for table");
		}
	}

	@Override
	public void deleteStock(Long stockId) {
		try {
			Stock savedStock = getStockById(stockId);
			stockRepository.deleteById(savedStock.getStockId());
		} catch (Exception e) {
			log.error(String.format("Stock with Id:%d not deleted an exception occured", stockId));
			throw new ResourceNotDeletedException(String.format("Stock with Id:%d not deleted", stockId));
		}
	}

}
