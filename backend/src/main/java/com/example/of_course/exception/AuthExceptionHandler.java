package com.example.of_course.exception;

import com.example.of_course.common.dto.ResponseMessageDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseMessageDto> handleInvalidCredentials(BadCredentialsException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = "Invalid credentials";
        String details = e.getMessage();

        ResponseMessageDto response = new ResponseMessageDto(status.value(), message, details);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ResponseMessageDto> handleMissingCredentials(AuthenticationCredentialsNotFoundException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Missing credentials";
        String details = e.getMessage();

        ResponseMessageDto response = new ResponseMessageDto(status.value(), message, details);
        return ResponseEntity.badRequest().body(response);
    }
}
