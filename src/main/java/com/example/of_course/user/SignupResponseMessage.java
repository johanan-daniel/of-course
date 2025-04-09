package com.example.of_course.user;

import com.example.of_course.dto.ResponseMessage;

public class SignupResponseMessage extends ResponseMessage {
    private String email;

    public SignupResponseMessage(int statusCode, String message, String details, String email) {
        super(statusCode, message, details);
        this.email = email;
    }

    public SignupResponseMessage(int statusCode, String message, String email) {
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
