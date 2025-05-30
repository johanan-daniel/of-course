package com.example.of_course.job.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "InterviewStage")
public class InterviewStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 50)
    private String label;


    public InterviewStage() {
    }

    public InterviewStage(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public InterviewStage(String label) {
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
        return "InterviewStage{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
