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
    @Column( name = "User_id")
    private int id;

    private String name;
    private String email;
    private String password;
    private String education;
    private String location;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteRecruit> favoriteRecruits;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteCorp> favoriteCorps;
}
