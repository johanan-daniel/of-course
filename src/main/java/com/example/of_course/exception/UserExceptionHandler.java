package com.example.of_course.exception;

import com.example.of_course.user.SignupResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity<SignupResponse> handleUserEmailAlreadyExists(UserEmailAlreadyExistsException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        String message = "Email already in use";
        String details = e.getMessage();
        String email = e.getEmail();

        SignupResponse response = new SignupResponse(status.value(), message, details, email);
        return ResponseEntity.status(status).body(response);
    }
}
