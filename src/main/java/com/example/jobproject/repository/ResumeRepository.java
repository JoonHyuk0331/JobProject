package com.example.jobproject.repository;

import com.example.jobproject.entity.Resume;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {

}
