package building.sma.market.advice;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import building.sma.market.exception.ResourceNotFetchedException;
import building.sma.market.model.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ResourceNotFetchedException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotAddedException(ResourceNotFetchedException rnfe,
	    HttpServletRequest request) {
	ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.NOT_IMPLEMENTED.value())
		.timestamp(LocalDateTime.now()).message(rnfe.getMessage()).path(request.getRequestURI()).build();
	return new ResponseEntity<>(message, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e, HttpServletRequest request) {
	ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
		.timestamp(LocalDateTime.now()).message(e.getMessage()).path(request.getRequestURI()).build();
	return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
