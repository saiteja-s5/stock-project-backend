package building.sma.market.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HistoricalQuote {

    private LocalDate tradedDate;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal adjustedClose;
    private Long volume;

    @Override
    public String toString() {
	return tradedDate + "," + open + "," + close + "," + high + "," + low + "," + adjustedClose + "," + volume;
    }

}