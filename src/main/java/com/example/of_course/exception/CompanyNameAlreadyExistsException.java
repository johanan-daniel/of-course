package com.example.of_course.exception;

public class CompanyNameAlreadyExistsException extends RuntimeException {
    private String companyName;

    public CompanyNameAlreadyExistsException(String message) {
        super(message);
    }

    public CompanyNameAlreadyExistsException(String message, String companyName) {
        super(message);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}
