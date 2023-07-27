package building.stockapp.service.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import building.stockapp.dto.MutualFundTableRowDTO;
import building.stockapp.model.MutualFund;
import building.stockapp.repository.MutualFundRepository;
import building.stockapp.service.MutualFundService;

@Service
public class MutualFundServiceImpl implements MutualFundService {

	private MutualFundRepository mutualFundRepository;
	private static final Logger LOGGER = Logger.getLogger(MutualFundServiceImpl.class.getName());

	public MutualFundServiceImpl(MutualFundRepository mutualFundRepository) {
		this.mutualFundRepository = mutualFundRepository;
	}

	@Override
	public MutualFund addMutualFund(MutualFund mutualFund) {
		LOGGER.log(Level.INFO, "Adding mutual fund {0}", mutualFund);
		MutualFund savedMutualFund = mutualFundRepository.save(mutualFund);
		LOGGER.log(Level.INFO, "MutualFund added sucessfully");
		return savedMutualFund;
	}

	@Override
	public List<MutualFundTableRowDTO> getMutualFundsForTable() {
		LOGGER.log(Level.INFO, "Getting all added mutual funds");
		List<MutualFundTableRowDTO> savedMutualFunds = mutualFundRepository.getAllMutualFundsForTable();
		LOGGER.log(Level.INFO, "Retrieved {0} mutual funds sucessfully", savedMutualFunds.size());
		return savedMutualFunds;
	}

}
