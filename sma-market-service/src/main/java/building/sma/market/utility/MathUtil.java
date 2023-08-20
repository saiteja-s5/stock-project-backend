package building.sma.market.utility;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

public class MathUtil {

    public static final int MONTHS_IN_YEAR = 12;

    public static final Double BROKER_MISC_CHARGES = 10.0;

    public static final String MISC_TABLE_PRIMARY_KEY = "MASTER-KEY";

    public static final LocalDate STOCK_START_DATE = LocalDate.of(2021, 1, 18);

    public static final LocalDate MUTUAL_FUND_START_DATE = LocalDate.of(2021, 8, 14);

    public static final LocalDate FUND_START_DATE = LocalDate.of(2021, 1, 18);

    public static final LocalDate DIVIDEND_START_DATE = LocalDate.of(2021, 3, 10);

    public static final LocalDate SOLD_START_DATE = LocalDate.of(2021, 1, 18);

    public static final LocalDate PORTFOLIO_START_DATE = LocalDate.of(2023, 1, 24);

    public static final LocalDate EPOCH_SECOND_EXCEEDS_INTEGER_ON = LocalDate.of(2038, 1, 19);

    private MathUtil() {
    }

    public static BigDecimal getPercentTarget(Double percent, Period holdDuration, int quantity, Double buyPrice) {
	int months = holdDuration.getYears() * MONTHS_IN_YEAR + holdDuration.getMonths()
		+ (holdDuration.getDays() > 0 ? 1 : 0);
	return roundTo((months * percent * buyPrice * 0.01) + buyPrice + (BROKER_MISC_CHARGES / quantity), 2);
    }

    public static BigDecimal percentageReturn(Double buyPrice, Double sellPrice) {
	return roundTo(((sellPrice - buyPrice) / buyPrice) * 100, 2);
    }

    public static BigDecimal roundTo(Double number, int places) {
	return BigDecimal.valueOf(Math.round(number * Math.pow(10, places)) / Math.pow(10, places));
    }

    public static Calendar localDateToCalender(LocalDate localDate) {
	ZoneId zoneId = ZoneId.systemDefault();
	Date date = Date.from(localDate.atStartOfDay(zoneId).toInstant());
	Calendar from = Calendar.getInstance();
	from.setTime(date);
	return from;
    }

    public static long localDateToEpochSecond(LocalDate localDate) {
	ZoneId zoneId = ZoneId.systemDefault();
	ZoneOffset zoneOffSet = zoneId.getRules().getOffset(LocalDateTime.now());
	LocalDate date = localDate;
	LocalTime time = LocalTime.parse("00:00:00");
	return date.toEpochSecond(time, zoneOffSet);
    }

    public static LocalDate epochSecondToLocalDate(int seconds) {
	return LocalDate.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault());
    }

}
