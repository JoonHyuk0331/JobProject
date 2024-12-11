package com.example.jobproject.repository;

import com.example.jobproject.entity.Recruit;
import com.example.jobproject.entity.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
    Page<Salary> findAll (Pageable pageable);
}
