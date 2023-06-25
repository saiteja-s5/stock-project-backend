package building.stockapp.utility;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class MathUtility {

	public static final int MONTHS_IN_YEAR = 12;

	public static final Double BROKER_MISC_CHARGES = 10.0;

	private MathUtility() {
	}

	public static Double getPercentTarget(double percent, Period holdDuration, int quantity, double buyPrice) {
		int months = holdDuration.getYears() * MONTHS_IN_YEAR + holdDuration.getMonths()
				+ (holdDuration.getDays() > 0 ? 1 : 0);
		return roundTo((months * percent * buyPrice * 0.01) + buyPrice + (BROKER_MISC_CHARGES / quantity), 2);
	}

	public static Double percentageReturn(double buyPrice, double sellPrice) {
		return roundTo(((sellPrice - buyPrice) / buyPrice) * 100, 2);
	}

	public static Double roundTo(double number, int places) {
		return Math.round(number * Math.pow(10, places)) / Math.pow(10, places);
	}

	public static Calendar localDateToCalender(LocalDate localDate) {
		ZoneId zoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(zoneId).toInstant());
		Calendar from = Calendar.getInstance();
		from.setTime(date);
		return from;
	}

}
