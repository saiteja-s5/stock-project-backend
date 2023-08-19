package building.sma.inventory.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StockPostResponseDTO {

    private Long id;
    private String stockName;

    @Override
    public String toString() {
	return id + "," + stockName;
    }

}
