package com.example.of_course.job.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String city;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private USState state;

    public Location() {
    }

    public Location(String city, USState state) {
        this.city = city;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public USState getState() {
        return state;
    }

    public void setState(USState state) {
        this.state = state;
    }
}
