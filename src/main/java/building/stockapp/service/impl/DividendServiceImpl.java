package building.stockapp.service.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import building.stockapp.model.Dividend;
import building.stockapp.repository.DividendRepository;
import building.stockapp.service.DividendService;

@Service
public class DividendServiceImpl implements DividendService {

	private DividendRepository dividendRepository;
	private static final Logger LOGGER = Logger.getLogger(DividendServiceImpl.class.getName());

	public DividendServiceImpl(DividendRepository dividendRepository) {
		this.dividendRepository = dividendRepository;
	}

	@Override
	public Dividend addDividend(Dividend dividend) {
		LOGGER.log(Level.INFO, "Adding dividend");
		Dividend savedDividend = dividendRepository.save(dividend);
		LOGGER.log(Level.INFO, "Dividend added sucessfully");
		return savedDividend;
	}

	@Override
	public List<Dividend> getDividends() {
		LOGGER.log(Level.INFO, "Getting all added dividends");
		List<Dividend> savedDividends = dividendRepository.findAll();
		LOGGER.log(Level.INFO, "Retrieved {0} dividends sucessfully", savedDividends.size());
		return savedDividends;
	}

}
