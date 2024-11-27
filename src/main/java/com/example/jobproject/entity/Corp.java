package com.example.jobproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Corp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "Corp_id")
    private int id;

    private String corp_title;
    private String corp_business_type;
    private String corp_ceo;
    private String corp_homepage_url;
    private String corp_business_content;
    private String corp_location;

    @OneToMany(mappedBy = "corp", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteCorp> favoriteCorps;
}
