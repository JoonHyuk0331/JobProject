package com.example.jobproject.entity;

import com.example.jobproject.dto.RecruitDTO;
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
    @Column(name = "recruit_id")
    private int id;

    @Column(name = "recruit_title")
    private String recruitTitle;

    @Column(name = "recruit_company")
    private String recruitCompany;

    @Column(name = "recruit_location")
    private String recruitLocation;

    @Column(name = "recruit_experience")
    private String recruitExperience;

    @Column(name = "recruit_jobtype")
    private String recruitJobType;

    @Column(name = "recruit_deadline")
    private String recruitDeadline;

    @Column(name = "recruit_requirement")
    private String recruitRequirement;

    @Column(name = "recruit_main_job_sectors")
    private String recruitMainJobSectors;

    @Column(name = "recruit_side_job_sectors")
    private String recruitSideJobSectors;

    // 해당 recruit에 관심있는 사용자 '들' 조회용
    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteRecruit> favoriteRecruits;

    // 해당 공고를 올린 회사의 id
    @ManyToOne
    @JoinColumn(name = "corp_id")
    private Corp corp;

    // DTO로 변환하는 메서드
    public RecruitDTO toDTO() {
        RecruitDTO dto = new RecruitDTO();
        dto.setId(this.id);
        dto.setRecruitTitle(this.recruitTitle);
        dto.setRecruitCompany(this.recruitCompany);
        dto.setRecruitLocation(this.recruitLocation);
        dto.setRecruitExperience(this.recruitExperience);
        dto.setRecruitJobType(this.recruitJobType);
        dto.setRecruitDeadline(this.recruitDeadline);
        dto.setRecruitRequirement(this.recruitRequirement);
        dto.setRecruitMainJobSectors(this.recruitMainJobSectors);
        dto.setRecruitSideJobSectors(this.recruitSideJobSectors);
        dto.setCorpId(this.corp.getId());//corp전체를 넣는게 아니고 id만 넣기
        return dto;
    }
}
