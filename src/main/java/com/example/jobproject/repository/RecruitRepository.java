package com.example.jobproject.repository;

import com.example.jobproject.entity.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RecruitRepository extends JpaRepository<Recruit,Integer> {
    Page<Recruit> findAll (Pageable pageable);

    Page<Recruit> findByRecruitTitleContaining(String keyword, Pageable pageable);
    Page<Recruit> findByRecruitCompanyContaining(String keyword, Pageable pageable);


    //지역별
    Page<Recruit> findByRecruitLocationContaining(String keyword, Pageable pageable);
    //경력별
    Page<Recruit> findByRecruitExperienceContaining(String keyword, Pageable pageable);
    //급여별
    Page<Recruit> findByRecruitSalaryGreaterThanEqual(int salary, Pageable pageable);
    //기술스택별
    Page<Recruit> findByRecruitMainJobSectorsContaining(String keyword, Pageable pageable);

    //id가 input id인 항목의 views를 하나 증가시켜서 update하는 쿼리문
    @Modifying
    @Query("update Recruit recruit set recruit.recruitViews = recruit.recruitViews + 1 where recruit.id = :inputid")
    int updateView(int inputid);

}
