package org.song.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        var errorResponseDTO = ErrorResponse.builder()
                .errorMessage("Validation error")
                .errorCode(status.value())
                .details(errors)
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

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityExistsException(EntityExistsException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        var errorResponseDTO = ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode(status.value())
                .build();

        return ResponseEntity.status(status).body(errorResponseDTO);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponse> handleNumberFormatException(NumberFormatException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        var errorResponseDTO = ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode(status.value())
                .build();

        return ResponseEntity.status(status).body(errorResponseDTO);
    }
}
