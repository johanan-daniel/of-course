package com.example.of_course.job.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CompanyDto {
    private int id;
//    @NotBlank(message = "Company name is required")
//    @Size(max = 100, message = "Company name must be less than 100 characters")
    private String name;

//    @Size(max = 500, message = "Careers page URL must be less than 500 characters")
    private String careersPageUrl;

    public CompanyDto() {
    }

    public CompanyDto(String name) {
        this.name = name;
    }

    public CompanyDto(String name, String careersPageUrl) {
        this.name = name;
        this.careersPageUrl = careersPageUrl;
    }

    public CompanyDto(int id, String name, String careersPageUrl) {
        this.id = id;
        this.name = name;
        this.careersPageUrl = careersPageUrl;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCareersPageUrl() {
        return careersPageUrl;
    }

    public void setCareersPageUrl(String careersPageUrl) {
        this.careersPageUrl = careersPageUrl;
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", careersPageUrl='" + careersPageUrl + '\'' +
                '}';
    }
}
