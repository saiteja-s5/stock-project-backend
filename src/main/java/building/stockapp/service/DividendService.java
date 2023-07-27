package building.stockapp.service;

import java.util.List;

import building.stockapp.dto.DividendTableRowDTO;
import building.stockapp.model.Dividend;

public interface DividendService {

	Dividend addDividend(Dividend dividend);

	List<DividendTableRowDTO> getDividendsForTable();

}
