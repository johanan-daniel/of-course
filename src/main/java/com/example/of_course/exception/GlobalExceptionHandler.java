package com.example.of_course.exception;

import com.example.of_course.dto.ResponseMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ResponseMessage> handleUserEmailAlreadyExists(UserEmailAlreadyExistsException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        String message = "Email already in use";
        String details = e.getMessage();
        ResponseMessage response = new ResponseMessage(status.value(), message, details);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseMessage> handleIllegalArgument(IllegalArgumentException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Invalid argument provided";
        String details = e.getMessage();
        ResponseMessage response = new ResponseMessage(status.value(), message, details);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseMessage> handleEntityNotFound(EntityNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "Could not find provided value";
        String details = e.getMessage();
        ResponseMessage response = new ResponseMessage(status.value(), message, details);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseMessage> handleInvalidCredentials(BadCredentialsException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = "Invalid credentials";
        String details = e.getMessage();
        ResponseMessage response = new ResponseMessage(status.value(), message, details);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseMessage> handleMissingCredentials(AuthenticationCredentialsNotFoundException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = "Missing credentials";
        String details = e.getMessage();
        ResponseMessage response = new ResponseMessage(status.value(), message, details);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseMessage> handleGeneralException(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "An error occurred while processing the request";
        String details = e.getMessage();
        ResponseMessage response = new ResponseMessage(status.value(), message, details);
        e.printStackTrace();
        return ResponseEntity.status(status).body(response);
    }
}
