package building.stockapp.exception;

public class MiscellaneousRecordNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MiscellaneousRecordNotExistException() {
		super("No rows in Miscellaneous Record Table");
	}

}
