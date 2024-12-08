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
    @Column(name="resume_id")
    private int id;

    private String title;
    private String content;

    //이 이력서를 쓴 사람의 정보
    @ManyToOne
    private User user;

}

