package org.resource.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        var errorResponseDTO = ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode(status.value())
                .build();

        return ResponseEntity.status(status).body(errorResponseDTO);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        var errorResponseDTO = ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode(status.value())
                .build();

        return ResponseEntity.status(status).body(errorResponseDTO);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpClientErrorException(HttpClientErrorException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        var errorResponseDTO = ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode(status.value())
                .build();

        return ResponseEntity.status(status).body(errorResponseDTO);
    }

}
