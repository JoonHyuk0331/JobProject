package com.example.jobproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ApplyList {
    //이력서 내용을 포함하는 User-Recruit 간 다대다 연결 테이블
    //관심채용x 실제지원o, 이력서 id도 포함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applyList_id")
    private int id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="recruit_id")
    private Recruit recruit;

    // 테이블 생성 날짜를 저장하는 필드
    @Column(name = "create_date")
    private LocalDateTime createDate;

    //-------------------------------
    @ManyToOne
    @JoinColumn(name="resume_id")
    private Resume resume;

}
