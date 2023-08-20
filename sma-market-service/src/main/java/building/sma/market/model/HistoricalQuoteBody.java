package building.sma.market.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalQuoteBody {

    private String market;
    private String symbol;
    private LocalDate from;
    private LocalDate to;
    private Interval interval;

}
