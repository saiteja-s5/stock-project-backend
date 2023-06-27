package building.stockapp.model;

import java.time.LocalDate;

import building.stockapp.utility.Interval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalQuoteBodyTemplate {

	String market;
	String symbol;
	LocalDate from;
	LocalDate to;
	Interval interval;

}
