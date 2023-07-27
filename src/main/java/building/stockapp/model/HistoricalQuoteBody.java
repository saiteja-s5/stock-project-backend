package building.stockapp.model;

import java.time.LocalDate;

import building.stockapp.utility.Interval;
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
