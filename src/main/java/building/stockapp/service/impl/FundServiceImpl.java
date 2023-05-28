package building.stockapp.service.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import building.stockapp.model.Fund;
import building.stockapp.repository.FundRepository;
import building.stockapp.service.FundService;

@Service
public class FundServiceImpl implements FundService {

	private FundRepository fundRepository;
	private static final Logger LOGGER = Logger.getLogger(FundServiceImpl.class.getName());

	public FundServiceImpl(FundRepository fundRepository) {
		this.fundRepository = fundRepository;
	}

	@Override
	public Fund addFund(Fund fund) {
		LOGGER.log(Level.INFO, "Adding fund");
		Fund savedFund = fundRepository.save(fund);
		LOGGER.log(Level.INFO, "Fund added sucessfully");
		return savedFund;
	}

	@Override
	public List<Fund> getFunds() {
		LOGGER.log(Level.INFO, "Getting all added funds");
		List<Fund> savedFunds = fundRepository.findAll();
		LOGGER.log(Level.INFO, "Retrieved {0} funds sucessfully", savedFunds.size());
		return savedFunds;
	}

}
