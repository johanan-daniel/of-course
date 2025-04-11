package com.example.of_course.job.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "careers_url", length = 500)
    private String careersPageUrl;

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public Company(String name, String careersPageUrl) {
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
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", careersUrl='" + careersPageUrl + '\'' +
                '}';
    }
}
