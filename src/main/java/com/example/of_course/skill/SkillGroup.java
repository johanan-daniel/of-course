package com.example.of_course.skill;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Skill_Group")
public class SkillGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 100)
    private String label;

    // references skillGroups field in Skill entity class
    @ManyToMany(mappedBy = "skillGroups")
    private Set<Skill> skills;


    public SkillGroup() {
    }

    public SkillGroup(String label) {
        this.label = label;
    }

    public SkillGroup(String label, Set<Skill> skills) {
        this.label = label;
        this.skills = skills;
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

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public SkillGroup(int id, String label) {
        this.id = id;
        this.label = label;
    }
}
