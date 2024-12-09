package com.example.jobproject.dto;

import com.example.jobproject.entity.Recruit;
import com.example.jobproject.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FavoriteRecuritDTO {

    private int id;
    private int user_id;
    private int recruit_id;
    private boolean bookmarked;
    private LocalDateTime createDate;
}
