package com.example.of_course.exception;

import com.example.of_course.common.dto.ResponseMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JobAttributeExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(CompanyNameAlreadyExistsException.class)
    public ResponseEntity<ResponseMessageDto> handleCompanyNameAlreadyExists(CompanyNameAlreadyExistsException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        String message = "Company name already exists";
        String details = e.getMessage();

        ResponseMessageDto response = new ResponseMessageDto(status.value(), message, details);
        return ResponseEntity.status(status).body(response);
    }

}
