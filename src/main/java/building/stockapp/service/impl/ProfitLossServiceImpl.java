package building.stockapp.service.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import building.stockapp.model.ProfitLoss;
import building.stockapp.repository.ProfitLossRepository;
import building.stockapp.service.ProfitLossService;

@Service
public class ProfitLossServiceImpl implements ProfitLossService {

	private ProfitLossRepository profitLossRepository;
	private static final Logger LOGGER = Logger.getLogger(ProfitLossServiceImpl.class.getName());

	public ProfitLossServiceImpl(ProfitLossRepository profitLossRepository) {
		this.profitLossRepository = profitLossRepository;
	}

	@Override
	public ProfitLoss addProfitLoss(ProfitLoss profitLoss) {
		LOGGER.log(Level.INFO, "Adding Profit/Loss {0}", profitLoss);
		ProfitLoss savedProfitLoss = profitLossRepository.save(profitLoss);
		LOGGER.log(Level.INFO, "ProfitLoss added sucessfully");
		return savedProfitLoss;
	}

	@Override
	public List<ProfitLoss> getProfitLoss() {
		LOGGER.log(Level.INFO, "Getting all added Profit/Loss entries");
		List<ProfitLoss> savedProfitLoss = profitLossRepository.findAll();
		LOGGER.log(Level.INFO, "Retrieved {0} Profit/Loss entries sucessfully", savedProfitLoss.size());
		return savedProfitLoss;
	}

}
