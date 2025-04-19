package com.example.of_course.job.repository;

import com.example.of_course.job.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}