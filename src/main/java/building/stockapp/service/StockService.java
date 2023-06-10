package building.stockapp.service;

import java.util.List;

import building.stockapp.model.Stock;

public interface StockService {

	Stock addStock(Stock stock);

	List<Stock> getStocks();

	Stock getStockById(Long stockId);

	void deleteStock(Long stockId);

}
