package building.stockapp.service;

import java.util.List;

import building.stockapp.dto.FundTableRowDto;
import building.stockapp.model.Fund;

public interface FundService {

	Fund addFund(Fund fund);

	List<FundTableRowDto> getFundsForTable();

}
