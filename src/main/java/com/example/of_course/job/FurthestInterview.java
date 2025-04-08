package com.example.of_course.job;

import jakarta.persistence.*;

@Entity
@Table(name = "FurthestInterview")
public class FurthestInterview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 50)
    private String label;


    public FurthestInterview() {
    }

    public FurthestInterview(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public FurthestInterview(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "FurthestInterview{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
