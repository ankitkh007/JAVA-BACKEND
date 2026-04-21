package com.ankit.week_5_relationships.exception;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ankit.week_5_relationships.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // handler Validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors); // 400 Bad Request
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFoundException(StudentNotFoundException ex,
            HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("NOT FOUND");
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); // 404 Not Found
    }
}
