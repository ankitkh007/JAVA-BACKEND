package com.ankit.week_2_backend_demo_spring.exception;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ankit.week_2_backend_demo_spring.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> hanldeStudentNotFound(StudentNotFoundException ex,
            HttpServletRequest request) {
        // exception --> Error DTO
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now().toString());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("NOT FOUND");
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateStudentException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateStudent(DuplicateStudentException ex,
            HttpServletRequest request) {
        // exception --> Error DTO
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now().toString());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setError("CONFLICT");
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors); // 400 Bad Request
    }
}
