package building.stockapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import building.stockapp.dto.StockTableRowDTO;
import building.stockapp.exception.ResourceNotAddedException;
import building.stockapp.exception.ResourceNotDeletedException;
import building.stockapp.exception.ResourceNotFoundException;
import building.stockapp.exception.TableEmptyException;
import building.stockapp.exception.TableFetchException;
import building.stockapp.model.Stock;
import building.stockapp.repository.StockRepository;
import building.stockapp.service.StockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class StockServiceImpl implements StockService {

	private final StockRepository stockRepository;

	private static final String ERROR_MESSAGE = "Stock table is empty";

	@Override
	public Long addStock(Stock stock) {
		log.info(String.format("Adding stock:%s", stock.getStockName()));
		Stock savedStock;
		try {
			savedStock = stockRepository.save(stock);
			log.info(String.format("Stock:%s added sucessfully", stock.getStockName()));
			return savedStock.getStockId();
		} catch (Exception e) {
			log.error(String.format("Stock:%s not added, an exception was occured ", stock.getStockId()), e);
			throw new ResourceNotAddedException("Stock:" + stock.getStockName() + " not added");
		}
	}

	@Override
	public Stock getStockById(Long stockId) {
		log.info(String.format("Getting Stock with Id:%d", stockId));
		try {
			Stock savedStock = stockRepository.findById(stockId).orElseThrow(
					() -> new ResourceNotFoundException(String.format("Stock with Id:%d not found", stockId)));
			log.info(String.format("Retrieved Stock with Id:%d sucessfully", savedStock.getStockId()));
			return savedStock;
		} catch (ResourceNotFoundException rnfe) {
			log.error(String.format("Stock with Id:%d not found, an exception occured ", stockId), rnfe);
			throw new ResourceNotFoundException(String.format("Stock with Id:%d not found", stockId));
		}
	}

	@Override
	public List<Stock> getStocks() {
		log.info("Getting all added stocks");
		try {
			List<Stock> savedStocks = stockRepository.findAll();
			if (savedStocks.isEmpty()) {
				log.error(ERROR_MESSAGE);
				throw new TableEmptyException(ERROR_MESSAGE);
			}
			log.info(String.format("Retrieved %d stocks sucessfully", savedStocks.size()));
			return savedStocks;
		} catch (Exception e) {
			log.error("Unable to fetch requested stocks, an exception was occured ", e);
			throw new TableFetchException("Unable to fetch requested stocks");
		}
	}

	@Override
	public List<StockTableRowDTO> getStocksForTable() {
		log.info("Getting all added stocks for table");
		try {
			List<StockTableRowDTO> savedStocks = stockRepository.getAllStocksForTable();
			if (savedStocks.isEmpty()) {
				log.error(ERROR_MESSAGE);
				throw new TableEmptyException(ERROR_MESSAGE);
			}
			log.info(String.format("Retrieved %d stocks sucessfully", savedStocks.size()));
			return savedStocks;
		} catch (Exception e) {
			log.error("Unable to fetch requested stocks for table, an exception was occured ", e);
			throw new TableFetchException("Unable to fetch requested stocks for table");
		}
	}

	@Override
	public void deleteStock(Long stockId) {
		log.info(String.format("Deleting Stock with Id:%d", stockId));
		try {
			stockRepository.deleteById(stockId);
			log.info(String.format("Deleted stock with Id:%d sucessfully", stockId));
		} catch (Exception e) {
			log.error(String.format("Stock with Id:%d not deleted", stockId));
			throw new ResourceNotDeletedException(String.format("Stock with Id:%d not deleted", stockId));
		}
	}

}
