package building.stockapp.service;

import java.util.List;

import building.stockapp.dto.ProfitLossTableRowDto;
import building.stockapp.model.ProfitLoss;

public interface ProfitLossService {

	ProfitLoss addProfitLoss(ProfitLoss profitLoss);

	List<ProfitLossTableRowDto> getProfitLossForTable();

}
