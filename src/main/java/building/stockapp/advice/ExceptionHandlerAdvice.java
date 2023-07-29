package building.stockapp.advice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import building.stockapp.exception.ResourceNotAddedException;
import building.stockapp.exception.ResourceNotDeletedException;
import building.stockapp.exception.ResourceNotFoundException;
import building.stockapp.exception.TableEmptyException;
import building.stockapp.exception.TableFetchException;
import building.stockapp.model.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException rnfe,
			HttpServletRequest request) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.NOT_FOUND.value())
				.timestamp(LocalDateTime.now()).message(rnfe.getMessage()).path(request.getRequestURI()).build();
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

	@ExceptionHandler(ResourceNotAddedException.class)
	public ResponseEntity<ErrorMessage> handleResourceNotAddedException(ResourceNotAddedException rnae,
			HttpServletRequest request) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.NOT_IMPLEMENTED.value())
				.timestamp(LocalDateTime.now()).message(rnae.getMessage()).path(request.getRequestURI()).build();
		return new ResponseEntity<>(message, HttpStatus.NOT_IMPLEMENTED);
	}

	@ExceptionHandler(ResourceNotDeletedException.class)
	public ResponseEntity<ErrorMessage> handleResourceNotDeletedException(ResourceNotDeletedException rnde,
			HttpServletRequest request) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.BAD_REQUEST.value())
				.timestamp(LocalDateTime.now()).message(rnde.getMessage()).path(request.getRequestURI()).build();
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TableEmptyException.class)
	public ResponseEntity<ErrorMessage> handleTableEmptyException(TableEmptyException tee, HttpServletRequest request) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.NOT_FOUND.value())
				.timestamp(LocalDateTime.now()).message(tee.getMessage()).path(request.getRequestURI()).build();
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TableFetchException.class)
	public ResponseEntity<ErrorMessage> handleTableFetchException(TableFetchException tfe, HttpServletRequest request) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.timestamp(LocalDateTime.now()).message(tfe.getMessage()).path(request.getRequestURI()).build();
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleException(Exception e, HttpServletRequest request) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.timestamp(LocalDateTime.now()).message(e.getMessage()).path(request.getRequestURI()).build();
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
