package com.example.jobproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FavoriteCorp {
    //User-Corp간 다대다 연결 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="User_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="Corp_id")
    private Corp corp;
}