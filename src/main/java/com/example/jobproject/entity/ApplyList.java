package com.example.jobproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ApplyList {
    //이력서 내용을 포함하는 User-Recruit 간 다대다 연결 테이블
    //관심채용x 실제지원o, 이력서 id도 포함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ApplyList_id")
    private int id;

    @ManyToOne
    @JoinColumn(name="User_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="Recruit_id")
    private Recruit recruit;
    //-------------------------------

    @OneToOne
    @JoinColumn(name="Resume_id")
    private Resume resume;

}
