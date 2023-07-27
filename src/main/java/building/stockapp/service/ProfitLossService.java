package building.stockapp.service;

import java.util.List;

import building.stockapp.dto.ProfitLossTableRowDTO;
import building.stockapp.model.ProfitLoss;

public interface ProfitLossService {

	ProfitLoss addProfitLoss(ProfitLoss profitLoss);

	List<ProfitLossTableRowDTO> getProfitLossForTable();

}
