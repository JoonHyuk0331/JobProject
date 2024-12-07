package com.example.jobproject.dto;

import com.example.jobproject.entity.Recruit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class RecruitDTO {
    public RecruitDTO (){}
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
    private int recruitSalary;
    private int recruitViews;
    private int corpId;  // 회사 ID만 포함

    public Page<RecruitDTO> toRecruitDTOPage(Page<Recruit> RecruitList){
        Page<RecruitDTO> recruitDtoPage = RecruitList.map(recruit -> RecruitDTO.builder()
                .id(recruit.getId())
                .recruitTitle(recruit.getRecruitTitle())
                .recruitCompany(recruit.getRecruitCompany())
                .recruitLocation(recruit.getRecruitLocation())
                .recruitExperience(recruit.getRecruitExperience())
                .recruitJobType(recruit.getRecruitJobType())
                .recruitDeadline(recruit.getRecruitDeadline())
                .recruitRequirement(recruit.getRecruitRequirement())
                .recruitMainJobSectors(recruit.getRecruitMainJobSectors())
                .recruitSideJobSectors(recruit.getRecruitSideJobSectors())
                .recruitSalary(recruit.getRecruitSalary())
                .recruitViews(recruit.getRecruitViews()) //조회 횟수
                .corpId(recruit.getCorp().getId())  // 회사 ID 매핑
                .build());
        return recruitDtoPage;
    }
}