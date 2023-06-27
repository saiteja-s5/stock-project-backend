package building.stockapp.utility;

import java.time.LocalDate;

import building.stockapp.service.impl.MarketServiceImpl;

public class Main {

	public static void main(String[] args) {
		new MarketServiceImpl().getHistory("NSE", "INFY", LocalDate.of(2021, 8, 14), LocalDate.now(), Interval.DAILY);
	}

}
