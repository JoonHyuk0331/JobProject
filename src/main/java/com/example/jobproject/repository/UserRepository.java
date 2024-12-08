package com.example.jobproject.repository;

import com.example.jobproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    //커스텀 쿼리
    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
