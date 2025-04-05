package com.example.of_course.exception;

import com.example.of_course.dto.ResponseMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ResponseMessage> handleUserEmailAlreadyExists(UserEmailAlreadyExistsException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        String message = e.getMessage();
        ResponseMessage response = new ResponseMessage(status.value(), message);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseMessage> handleIllegalArgument(IllegalArgumentException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();
        ResponseMessage response = new ResponseMessage(status.value(), message);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseMessage> handleEntityNotFound(EntityNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = e.getMessage();
        ResponseMessage response = new ResponseMessage(status.value(), message);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseMessage> handleGeneralException(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = e.getMessage();
        ResponseMessage response = new ResponseMessage(status.value(), message);
        return ResponseEntity.status(status).body(response);
    }
}
