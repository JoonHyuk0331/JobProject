package com.example.jobproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Recruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Recruit_id")
    private int id;

    private String recruit_title;
    private String recruit_company;
    private String recruit_location;
    private String recruit_experience;
    private String recruit_jobtype;

    //@Column(columnDefinition = "date")
    private String recruit_deadline;

    private String recruit_requirement;
    private String recruit_main_job_sectors;
    private String recruit_side_job_sectors;

    //해당 recruit에 관심있는 사용자'들' 조회용
    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteRecruit> favoriteRecruits;

    //해당 공고를 올린 회사의 id
    @ManyToOne
    @JoinColumn(name="Corp_id")
    private Corp corp;

}
