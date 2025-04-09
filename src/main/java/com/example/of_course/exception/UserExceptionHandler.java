package com.example.of_course.exception;

import com.example.of_course.dto.ResponseMessage;

import com.example.of_course.user.SignupResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity<SignupResponseMessage> handleUserEmailAlreadyExists(UserEmailAlreadyExistsException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        String message = "Email already in use";
        String details = e.getMessage();
        String email = e.getEmail();

        SignupResponseMessage response = new SignupResponseMessage(status.value(), message, details, email);
        return ResponseEntity.status(status).body(response);
    }
}
