package com.example.jobproject.repository;

import com.example.jobproject.entity.Corp;
import com.example.jobproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorpRepository extends JpaRepository<Corp, Integer> {
    Corp findByCorpTitle(String corpTitle);  // 메서드명 수정
}