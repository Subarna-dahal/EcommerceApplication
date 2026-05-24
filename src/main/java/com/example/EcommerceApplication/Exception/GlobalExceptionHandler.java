package com.example.EcommerceApplication.Exception;


import jakarta.servlet.http.PushBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> buildResponse(HttpStatus status, String message, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("Error", status.getReasonPhrase());
        body.put("message", message);
        body.put("status", status.value());
        body.put("path", request.getDescription(false));
        return body;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleproductNotfound(ProductNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleOrderNotfound(OrderNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotfound(UserNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficentStock(InsufficientStockException exception, WebRequest request) {
        return new ResponseEntity<>(buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidOrderEcxeption.class)
    public ResponseEntity<Map<String, Object>> handleInvalidOrder(InvalidOrderEcxeption ecxeption, WebRequest request) {
        return new ResponseEntity<>(buildResponse(HttpStatus.NOT_FOUND, ecxeption.getMessage(), request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<Map<String,Object>> handleInvalidCredential(InvalidCredentialException exception,WebRequest request){
        return new ResponseEntity<>(buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request),HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Map<String,Object>> handleDuplicateUser(DuplicateUserException exception,WebRequest request){
        return new ResponseEntity<>(buildResponse(HttpStatus.NOT_FOUND,exception.getMessage(),request),HttpStatus.NOT_FOUND);
    }
}

