package com.example.of_course.job.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 100)
    private String label;

    // Without having to manually call save on a new or updated SkillGroup
    // CascadeType.PERSIST saves new SkillGroups that are added to this list
    // CascadeType.MERGE will update a SkillGroup that gets updated
    // Skill owns the relationship
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    // joinColumns defines the FK column in join table that refers to PK of Skill
    // inverseJoinColumns defines FK column in join table that refers to PK of SkillGroup (other entity)
    // the names provided refer to those in the JOIN TABLE
    @JoinTable(
            name = "skill_grouped_with",
            joinColumns = @JoinColumn(name = "skill_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_group_id")
    )
    private Set<SkillGroup> skillGroups;


    // CHECK (will need to manually call save)
    // ensures consistency (not needed?)
    public void addSkillGroup(SkillGroup skillGroup) {
        this.skillGroups.add(skillGroup);
        skillGroup.getSkills().add(this);
    }

    // CHECK (will need to manually call save)
    // ensures consistency (not needed?)
    public void removeSkillGroup(SkillGroup skillGroup) {
        this.skillGroups.remove(skillGroup);
        skillGroup.getSkills().remove(this);
    }


    public Skill() {
    }

    public Skill(String label) {
        this.label = label;
    }

    public Skill(String label, Set<SkillGroup> skillGroups) {
        this.label = label;
        this.skillGroups = skillGroups;
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

    public Set<SkillGroup> getSkillGroups() {
        return skillGroups;
    }

    public void setSkillGroups(Set<SkillGroup> skillGroups) {
        this.skillGroups = skillGroups;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
