package building.sma.inventory.exception;

public class ResourceNotAddedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotAddedException(String message) {
	super(message);
    }

}
