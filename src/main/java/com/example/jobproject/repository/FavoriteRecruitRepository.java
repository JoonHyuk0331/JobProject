package com.example.jobproject.repository;

import com.example.jobproject.entity.FavoriteRecruit;
import com.example.jobproject.entity.Recruit;
import com.example.jobproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRecruitRepository extends JpaRepository<FavoriteRecruit, Integer> {
    // 특정 사용자의 특정 채용공고에 대한 북마크를 찾기
    Optional<FavoriteRecruit> findByUserAndRecruit(User user, Recruit recruit);

    // 사용자가 북마크한 모든 북마크들을 가져오기
    List<FavoriteRecruit> findByUser(User user);
}
