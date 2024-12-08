package com.example.jobproject.repository;

import com.example.jobproject.entity.ApplyList;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyListRepository extends JpaRepository<ApplyList, Integer> {
}
