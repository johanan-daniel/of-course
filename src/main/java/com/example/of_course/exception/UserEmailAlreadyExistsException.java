package com.example.of_course.exception;

public class UserEmailAlreadyExistsException extends RuntimeException {
    private String email;

    public UserEmailAlreadyExistsException(String message) {
        super(message);
    }

    public UserEmailAlreadyExistsException(String message, String email) {
        super(message);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
