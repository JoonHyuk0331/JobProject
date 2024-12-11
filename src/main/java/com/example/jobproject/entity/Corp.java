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
    @Column(name = "corp_id")
    private int id;

    @Column(name = "corp_title")
    private String corpTitle;

    @Column(name = "corp_business_type")
    private String corpBusinessType;

    @Column(name = "corp_ceo")
    private String corpCeo;

    @Column(name = "corp_homepage_url")
    private String corpHomepageUrl;

    @Column(name = "corp_business_content")
    private String corpBusinessContent;

    @Column(name = "corp_location")
    private String corpLocation;

    @Column(name = "corp_salary")
    private int corpSalary;

    // 회사는 여러 개의 recruit 공고를 낼 수 있다
    @OneToMany(mappedBy = "corp")
    private List<Recruit> recruits;

    // 회사의 세부 정보 테이블
    @OneToOne
    @JoinColumn(name="corp_detail_id")
    private CorpDetail corpDetail;
}
