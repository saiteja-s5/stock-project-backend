package building.sma.inventory.service;

import building.sma.inventory.dto.StockPostRequestDTO;
import building.sma.inventory.dto.StockPostResponseDTO;

public interface StockService {

    StockPostResponseDTO postStock(StockPostRequestDTO stockPostRequestDTO);

}
