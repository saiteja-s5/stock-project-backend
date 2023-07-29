package building.stockapp.service;

import java.util.List;

import building.stockapp.dto.StockTableRowDTO;
import building.stockapp.model.Stock;

public interface StockService {

	Long postStock(Stock stock);

	Stock getStockById(Long stockId);

	List<Stock> getStocks();

	List<StockTableRowDTO> getStocksForTable();

	void deleteStock(Long stockId);

}
