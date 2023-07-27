package building.stockapp.service;

import java.util.List;

import building.stockapp.dto.FundTableRowDTO;
import building.stockapp.model.Fund;

public interface FundService {

	Fund addFund(Fund fund);

	List<FundTableRowDTO> getFundsForTable();

}
