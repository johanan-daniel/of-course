package com.example.of_course.exception;

import com.example.of_course.user.dto.SignupResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity<SignupResponseDto> handleUserEmailAlreadyExists(UserEmailAlreadyExistsException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        String message = "Email already in use";
        String details = e.getMessage();
        String email = e.getEmail();

        SignupResponseDto response = new SignupResponseDto(status.value(), message, details, email);
        return ResponseEntity.status(status).body(response);
    }
}
