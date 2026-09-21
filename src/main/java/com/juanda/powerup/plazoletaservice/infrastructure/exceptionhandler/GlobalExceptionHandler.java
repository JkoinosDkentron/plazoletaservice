package com.juanda.powerup.plazoletaservice.infrastructure.exceptionhandler;

import com.juanda.powerup.plazoletaservice.domain.exception.InvalidRestaurantException;
import com.juanda.powerup.plazoletaservice.domain.exception.OwnerValidationUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidRestaurantException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRestaurant(InvalidRestaurantException exception) {
        return error(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleMalformedRequest(Exception exception) {
        return error(HttpStatus.BAD_REQUEST, "Invalid request body or identifier");
    }

    @ExceptionHandler(OwnerValidationUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleUnavailableOwnerValidation(
            OwnerValidationUnavailableException exception) {
        return error(HttpStatus.SERVICE_UNAVAILABLE, "Owner validation is temporarily unavailable");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception exception) {
        LOGGER.error("Unexpected restaurant request failure", exception);
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
    }

    private ResponseEntity<ErrorResponse> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(
                new ErrorResponse(Instant.now(), status.value(), status.getReasonPhrase(), message));
    }
}
