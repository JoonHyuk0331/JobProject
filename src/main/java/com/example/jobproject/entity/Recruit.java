package com.example.jobproject.entity;

import com.example.jobproject.dto.RecruitDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

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

    @Column(name = "recruit_salary",columnDefinition = "integer default 0")
    private Integer recruitSalary=0;

    @Column(name = "recruit_views" ,columnDefinition = "integer default 0")
    private int recruitViews;

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
        dto.setRecruitSalary(this.recruitSalary);
        dto.setRecruitViews(this.recruitViews);
        //DTO 변환중 Corp가 null 값일때 오류 발생 방지
        Corp corp = null; // null 허용, 회사id 없는 JSON 전달받으면 null값으로 집어넣기
        if (this.corp != null) {// null값이 아니면
            dto.setCorpId(this.corp.getId());//corp전체를 넣는게 아니고 id만 넣기
        }
        return dto;
    }
}
