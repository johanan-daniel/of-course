package com.example.of_course.common.dto;

public class ResponseMessageDto {
    private int statusCode = 200;
    private String message = "OK";
    private String details;

    public ResponseMessageDto(int statusCode, String message, String details) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }

    public ResponseMessageDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ResponseMessageDto(String message) {
        this.message = message;
    }


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}