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
    @Column(name = "User_id")
    private int id;

    private String username;
    private String password;
    private String role;

    private String education;
    private String location;



    //유저는 여러 Recruit 에 관심을 가질 수 있다
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteRecruit> favoriteRecruits;

    //유저는 여러 Crop 에 관심을 가질 수 있다
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteCorp> favoriteCorps;

    //유저는 여러개의 이력서를 가질 수 있다
    @OneToMany(mappedBy = "user")
    private List<Resume> resumes;
}