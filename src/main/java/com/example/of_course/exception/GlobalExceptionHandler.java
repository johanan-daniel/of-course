package com.example.of_course.exception;

import com.example.of_course.dto.ResponseMessage;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// ahhhhhhhhhh the ControllerAdvice classes get prioritized BY THEIR LEXICOGRAPHICAL ORDER
//  and NOT THEIR EXCEPTION SPECIFICITY what the heck is this spring boot smh i shoulda used .net
//  https://github.com/spring-projects/spring-framework/issues/31818
//  also the default precedence for any controlleradvice is LOWEST?????? WHY NOT JUST 0 ahhhhhhhhh
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleEntityNotFound(EntityNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "Could not find provided value";
        String details = e.getMessage();

        ResponseMessage response = new ResponseMessage(status.value(), message, details);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> handleGeneralException(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "An error occurred while processing the request";
        String cause = e.getClass().getSimpleName();
        String details = cause + " – " + e.getMessage();

//        e.printStackTrace();

        ResponseMessage response = new ResponseMessage(status.value(), message, details);
        return ResponseEntity.status(status).body(response);
    }
}
