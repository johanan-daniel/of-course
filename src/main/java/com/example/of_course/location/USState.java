package com.example.of_course.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "US_State")
public class USState {
    @Id
    @Column(length = 2)
    private String id;

    @Column(nullable = false, length = 50)
    private String name;

    public USState() {
    }

    public USState(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
