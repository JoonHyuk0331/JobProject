package com.example.jobproject.dto;

import com.example.jobproject.entity.Corp;
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
    private Integer corpId;  // 회사 ID만 포함 null값 허용해야되어서 Integer로 변환함

    public Page<RecruitDTO> toRecruitDTOPage(Page<Recruit> RecruitList){
        Integer corpid = null; // null 허용, 회사id 없는 JSON 전달받으면 null값으로 집어넣기
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
                .corpId(recruit.getCorp() != null ? recruit.getCorp().getId() : null) // null 체크
                .build());
        return recruitDtoPage;
    }
}