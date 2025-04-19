package com.example.of_course.exception;

import com.example.of_course.common.dto.ResponseMessageDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseMessageDto> handleIllegalArgument(IllegalArgumentException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Invalid argument provided";
        String details = e.getMessage();

        ResponseMessageDto response = new ResponseMessageDto(status.value(), message, details);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessageDto> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        HttpStatusCode status = e.getStatusCode();
        String message = "Invalid argument provided";
        String details = e.getBody().getDetail();

        ResponseMessageDto response = new ResponseMessageDto(status.value(), message, details);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseMessageDto> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Malformed request";
        String details = e.getMessage();

        ResponseMessageDto response = new ResponseMessageDto(status.value(), message, details);
        return ResponseEntity.badRequest().body(response);
    }
}
