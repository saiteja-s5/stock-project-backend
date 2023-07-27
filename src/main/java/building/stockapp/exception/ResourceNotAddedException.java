package building.stockapp.exception;

public class ResourceNotAddedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotAddedException(String message) {
		super(message);
	}
}
