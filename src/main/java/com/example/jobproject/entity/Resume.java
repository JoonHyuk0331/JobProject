package com.example.jobproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Resume_id")
    private int id;

    private String title;
    private String content;

    //이 이력서를 쓴 사람의 정보
    @ManyToOne
    private User user;

//    @OneToOne
//    @JoinColumn(name = "ApplyList_id")
//    private ApplyList applyList;
}
//각 사용자가 여러 ApplyList에 여러 Resume을 지원할 수 있다면
// OneToMany 또는 ManyToOne 관계가 적합함. <-맞는지모르겠다?
