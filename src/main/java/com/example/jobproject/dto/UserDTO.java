package com.example.jobproject.dto;

import com.example.jobproject.entity.ApplyList;
import com.example.jobproject.entity.FavoriteCorp;
import com.example.jobproject.entity.FavoriteRecruit;
import com.example.jobproject.entity.Resume;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {

    private int id;

    private String username;
    private String password;
    private String role;

    private String education;
    private String location;

}
