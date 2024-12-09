package com.example.jobproject.entity;

import com.example.jobproject.dto.FavoriteRecuritDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class FavoriteRecruit {
    //User-Recruit간 다대다 연결 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="recruit_id")
    private Recruit recruit;

    private boolean bookmarked;//북마크 상태 토글

    @Column(name = "create_date")
    private LocalDateTime createDate;

}
