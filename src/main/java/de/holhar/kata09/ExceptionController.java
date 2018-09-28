package de.holhar.kata09;

import de.holhar.kata09.dto.CheckoutResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author hharms
 */
@ControllerAdvice
public class ExceptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<CheckoutResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        LOGGER.error(e.getMessage(), e);
        CheckoutResponse response = new CheckoutResponse(false, e.getMessage(), 0);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON_UTF8).body(response);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CheckoutResponse> handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        CheckoutResponse response = new CheckoutResponse(false, e.getMessage(), 0);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON_UTF8).body(response);
    }
}
