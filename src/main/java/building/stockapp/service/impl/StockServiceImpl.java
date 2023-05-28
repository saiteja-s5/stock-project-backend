package building.stockapp.service.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import building.stockapp.model.Stock;
import building.stockapp.repository.StockRepository;
import building.stockapp.service.StockService;

@Service
public class StockServiceImpl implements StockService {

	private StockRepository stockRepository;
	private static final Logger LOGGER = Logger.getLogger(StockServiceImpl.class.getName());

	public StockServiceImpl(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	@Override
	public Stock addStock(Stock stock) {
		LOGGER.log(Level.INFO, "Adding stock {0}", stock.getStockName());
		Stock savedStock = stockRepository.save(stock);
		LOGGER.log(Level.INFO, "Stock {0} added sucessfully", stock.getStockName());
		return savedStock;
	}

	@Override
	public List<Stock> getStocks() {
		LOGGER.log(Level.INFO, "Getting all added stocks");
		List<Stock> savedStocks = stockRepository.findAll();
		LOGGER.log(Level.INFO, "Retrieved {0} stocks sucessfully", savedStocks.size());
		return savedStocks;
	}

}
