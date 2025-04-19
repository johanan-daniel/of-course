package com.example.of_course.user.dto;

import com.example.of_course.common.dto.ResponseMessageDto;

public class SignupResponseDto extends ResponseMessageDto {
    private String email;

    public SignupResponseDto(int statusCode, String message, String details, String email) {
        super(statusCode, message, details);
        this.email = email;
    }

    public SignupResponseDto(int statusCode, String message, String email) {
        super(statusCode, message);
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
