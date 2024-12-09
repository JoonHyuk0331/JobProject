package com.example.jobproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    private String username;
    private String password;
    private String role;

    private String education;
    private String location;

    // 유저는 여러 Recruit에 지원할 수 있다
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApplyList> applyList;

    // 유저는 여러 Recruit에 관심을 가질 수 있다
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteRecruit> favoriteRecruits;

    // 유저는 여러 Corp에 관심을 가질 수 있다
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteCorp> favoriteCorps;

    // 유저는 여러 개의 이력서를 가질 수 있다
    @OneToMany(mappedBy = "user")
    private List<Resume> resumes;
}
