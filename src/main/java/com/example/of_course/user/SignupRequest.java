package com.example.of_course.user;

public class SignupRequest {
    private String email = "";
    private String password = "";
    //FIXME make null?
    private String name = "";

    public SignupRequest() {
    }

    public SignupRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public SignupRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
