package building.stockapp.exception;

public class ResourceNotDownloadedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotDownloadedException(String message) {
		super(message);
	}

}
