package building.stockapp.service;

import java.util.List;

import building.stockapp.dto.MutualFundTableRowDto;
import building.stockapp.model.MutualFund;

public interface MutualFundService {

	MutualFund addMutualFund(MutualFund mutualFund);

	List<MutualFundTableRowDto> getMutualFundsForTable();

}
