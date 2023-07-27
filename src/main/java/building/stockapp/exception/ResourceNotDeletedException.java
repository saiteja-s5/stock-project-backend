package building.stockapp.exception;

public class ResourceNotDeletedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotDeletedException(String message) {
		super(message);
	}

}
