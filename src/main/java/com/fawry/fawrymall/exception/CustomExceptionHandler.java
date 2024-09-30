package com.fawry.fawrymall.exception;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss a";

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionMessage> entityNotFoundExceptionHandler(EntityNotFoundException e) {
        return generateExceptionResponseEntity(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionMessage> NoSuchElementExceptionHandler(NoSuchElementException e) {
        return generateExceptionResponseEntity(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ExceptionMessage exceptionMessage = generateExceptionResponseEntity(e, HttpStatus.BAD_REQUEST).getBody();

        ExceptionValidationMessage exceptionValidationResponse = new ExceptionValidationMessage(
                Objects.requireNonNull(exceptionMessage),
                errors
        );
        return new ResponseEntity<>(exceptionValidationResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionMessage> IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        return generateExceptionResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionMessage> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return generateExceptionResponseEntity(e, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return generateExceptionResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionMessage> handleNullPointerException(NullPointerException e) {
        return generateExceptionResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception e) {
        return generateExceptionResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionMessage> generateExceptionResponseEntity(Exception e, HttpStatus status) {
        ExceptionMessage errorBody = new ExceptionMessage(
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
        );
        return ResponseEntity.status(status).body(errorBody);
    }

}
