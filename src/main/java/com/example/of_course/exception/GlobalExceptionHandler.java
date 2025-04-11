package com.example.of_course.exception;

import com.example.of_course.dto.ResponseMessage;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

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

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseMessage> handleHttpMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        String message = status.getReasonPhrase();
        String details = e.getMessage();
        Set<HttpMethod> allowed = e.getSupportedHttpMethods();

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(status);
        if (allowed != null) {
            responseBuilder.allow(allowed.toArray(new HttpMethod[0]));
        }
        ResponseMessage responseBody = new ResponseMessage(status.value(), message, details);
        return responseBuilder.body(responseBody);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> handleGeneralException(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "An error occurred while processing the request";
        String cause = e.getClass().getSimpleName();
        String details = cause + " – " + e.getMessage();

        // TODO only do when profile is dev?
        e.printStackTrace();

        ResponseMessage response = new ResponseMessage(status.value(), message, details);
        return ResponseEntity.status(status).body(response);
    }
}
