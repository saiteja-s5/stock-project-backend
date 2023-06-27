package building.stockapp.utility;

public enum Interval {

	DAILY("1d"), WEEKLY("5d"), MONTHLY("1mo");

	private final String tag;

	Interval(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return this.tag;
	}

}
