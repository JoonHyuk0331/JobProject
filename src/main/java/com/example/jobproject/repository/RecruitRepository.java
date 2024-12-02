package com.example.jobproject.repository;

import com.example.jobproject.entity.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitRepository extends JpaRepository<Recruit,Integer> {
    Page<Recruit> findAll (Pageable pageable);
}
