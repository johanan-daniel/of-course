package com.example.of_course.job.repository;

import com.example.of_course.job.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Boolean existsByName(String name);
    List<Company> findByNameContainingIgnoreCase(String name);
}