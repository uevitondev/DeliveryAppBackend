package com.uevitondev.deliveryapp.config;

import com.uevitondev.deliveryapp.exceptions.AuthorizationException;
import com.uevitondev.deliveryapp.exceptions.DatabaseException;
import com.uevitondev.deliveryapp.exceptions.ResourceNotFoundException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERRORS = "errors";
    private static final String STACKTRACE = "stackTrace";

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ProblemDetail> resourceNotFound(ResourceNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Resource Not Found Error");
        problemDetail.setType(URI.create("https://example.com/resource-not-found"));
        problemDetail.setProperty(ERRORS, List.of(e.getLocalizedMessage()));
        problemDetail.setProperty(STACKTRACE, e.getStackTrace());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ProblemDetail> badCredentials(BadCredentialsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setTitle("Credentials Invalid");
        problemDetail.setType(URI.create("https://example.com/credentials-error"));
        problemDetail.setProperty(ERRORS, List.of(e.getLocalizedMessage()));
        problemDetail.setProperty(STACKTRACE, e.getStackTrace());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }

    @ExceptionHandler(AuthorizationException.class)
    protected ResponseEntity<ProblemDetail> noAuthorization(AuthorizationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setTitle("Authorization Error");
        problemDetail.setType(URI.create("https://example.com/authorization-error"));
        problemDetail.setProperty(ERRORS, List.of(e.getLocalizedMessage()));
        problemDetail.setProperty(STACKTRACE, e.getStackTrace());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }


    @ExceptionHandler(DatabaseException.class)
    protected ResponseEntity<ProblemDetail> dataIntegrityViolation(DatabaseException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Database Error");
        problemDetail.setType(URI.create("https://example.com/database-error"));
        problemDetail.setProperty(ERRORS, List.of(e.getLocalizedMessage()));
        problemDetail.setProperty(STACKTRACE, e.getStackTrace());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        super.handleMethodArgumentNotValid(e, headers, status, request);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        problemDetail.setTitle("Validation Error");
        problemDetail.setType(URI.create("https://example.com/validation-error"));
        problemDetail.setProperty(ERRORS, e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList());
        problemDetail.setProperty(STACKTRACE, e.getStackTrace());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problemDetail);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        problemDetail.setTitle("Invalid JSON Payload");
        problemDetail.setType(URI.create("https://example.com/json-parse-error"));
        problemDetail.setProperty(ERRORS, List.of(e.getLocalizedMessage()));
        problemDetail.setProperty(STACKTRACE, e.getStackTrace());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problemDetail);
    }
}
