package building.stockapp.exception;

public class TableEmptyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TableEmptyException(String message) {
		super(message);
	}

}
