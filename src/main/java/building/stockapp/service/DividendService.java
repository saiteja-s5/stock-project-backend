package building.stockapp.service;

import java.util.List;

import building.stockapp.model.Dividend;

public interface DividendService {

	Dividend addDividend(Dividend dividend);

	List<Dividend> getDividends();

}
