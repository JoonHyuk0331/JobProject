package com.example.jobproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruitDTO {
    private int id;
    private String recruitTitle;
    private String recruitCompany;
    private String recruitLocation;
    private String recruitExperience;
    private String recruitJobType;
    private String recruitDeadline;
    private String recruitRequirement;
    private String recruitMainJobSectors;
    private String recruitSideJobSectors;
    private int corpId;  // 회사 ID만 포함

}