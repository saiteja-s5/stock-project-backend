package building.stockapp.exception;

public class StockNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StockNotFoundException(Long stockId) {
		super("Could not find Stock with Id " + stockId);

	}

}
