package building.stockapp.advice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import building.stockapp.exception.MiscellaneousRecordNotExistException;
import building.stockapp.exception.StockNotFoundException;
import building.stockapp.model.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(MiscellaneousRecordNotExistException.class)
	public ResponseEntity<ErrorMessage> handleMiscellaneousRecordNotExistException(
			MiscellaneousRecordNotExistException mrnee, HttpServletRequest request) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.NOT_FOUND.value())
				.timestamp(LocalDateTime.now()).message(mrnee.getMessage()).path(request.getRequestURI()).build();
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(StockNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleStockNotFoundException(StockNotFoundException snfe,
			HttpServletRequest request) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.NOT_FOUND.value())
				.timestamp(LocalDateTime.now()).message(snfe.getMessage()).path(request.getRequestURI()).build();
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException manve,
			HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		manve.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.BAD_REQUEST.value())
				.errors(errors.toString()).timestamp(LocalDateTime.now()).message(manve.getMessage())
				.path(request.getRequestURI()).build();
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleException(Exception e, HttpServletRequest request) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.timestamp(LocalDateTime.now()).message(e.getMessage()).path(request.getRequestURI()).build();
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
