package com.example.jobproject.controller;

import com.example.jobproject.dto.CorpDetailDTO;
import com.example.jobproject.dto.RecruitDTO;
import com.example.jobproject.entity.Recruit;
import com.example.jobproject.service.CorpDetailService;
import com.example.jobproject.service.JobService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class JobController {

    @Autowired
    private JobService jobService;
    @Autowired
    private CorpDetailService corpDetailService;

    //1.채용 공고 조회 API (페이징)-------------------------------------------------------------------------------------
    @Tag(name = "예제 API", description = "Swagger 테스트용 API")
    @GetMapping("/jobs/joblist")
    public Map<String, Object> recruitList(@RequestParam(defaultValue = "0") int page) {
        Page<RecruitDTO> recruitPage = jobService.getListAll(page);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", recruitPage);  // DTO 리스트로 반환
        return response;
    }
    //todo: 정렬 기준 추가해야함

    //2-1.채용 공고 검색 API--------------------------------------------------------------------------------------------
    //    키워드 검색
    @GetMapping("/jobs/search/keyword")
    public Map<String ,Object> keyword(@RequestParam String keyword,@RequestParam(defaultValue = "0") int page) {
        Page<RecruitDTO> recruitPage = jobService.getListKeyword(keyword,page);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", recruitPage);
        return response;
    }
    //    회사명 검색
    @GetMapping("/jobs/search/corp")
    public Map<String ,Object> corp(@RequestParam String keyword,@RequestParam(defaultValue = "0") int page) {
        Page<RecruitDTO> recruitPage = jobService.getListCorp(keyword,page);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", recruitPage);
        return response;
    }
    //    포지션 검색
    @GetMapping("/jobs/search/sector")
    public Map<String ,Object> sector(@RequestParam String keyword,@RequestParam(defaultValue = "0") int page) {
        Page<RecruitDTO> recruitPage = jobService.getListSector(keyword,page);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", recruitPage);
        return response;
    }

    //2-2.채용 공고 필터링 API -------------------------------------------------------------------------------------------
    //지역별
    @GetMapping("/jobs/filter/location")
    public Map<String ,Object> filterLocation(@RequestParam String keyword,@RequestParam(defaultValue = "0") int page) {
        Page<RecruitDTO> recruitPage = jobService.getListLocation(keyword,page);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", recruitPage);
        return response;
    }
    //경력별
    @GetMapping("/jobs/filter/experience")
    public Map<String ,Object> filterExperience(@RequestParam String keyword,@RequestParam(defaultValue = "0") int page) {
        Page<RecruitDTO> recruitPage = jobService.getListExperience(keyword,page);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", recruitPage);
        return response;
    }
    //급여별
    @GetMapping("/jobs/filter/salary")
    public Map<String ,Object> filterSalary(@RequestParam int money,@RequestParam(defaultValue = "0") int page) {
        Page<RecruitDTO> recruitPage = jobService.getListSalary(money,page);//money(단위 만) 이상의 급여 행만 반환
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", recruitPage);
        return response;
    }
    //기술스택
    @GetMapping("/jobs/filter/skill")
    public Map<String ,Object> filterJobSector(@RequestParam String keyword,@RequestParam(defaultValue = "0") int page) {
        Page<RecruitDTO> recruitPage = jobService.getListJobSector(keyword,page);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", recruitPage);
        return response;
    }
    //2-3.공고 상세 조회 API ---------------------------------------------------------------------------------------------
    //상세 정보 제공
    @GetMapping("/jobs/{id}")
    public Map<String ,Object> detail(@PathVariable int id) {
        //a.공고내용[recruitDTO]
        RecruitDTO recruitDTO= jobService.getRecruitById(id);
        //b.회사상세정보[corpDetailDTO]
        int corpDetailId=recruitDTO.getCorpId();//일단 디비가 회사id=회사 상세id 여서 이렇게 해도 돌아감
        CorpDetailDTO corpDetailDTO = corpDetailService.getCorpDetailById(corpDetailId);
        //c.조회수[recruitViews]
        //detail 함수 실행시마다 해당id에 해당하는 recruit의 조회수 +1
        jobService.updateView(id);
        recruitDTO= jobService.getRecruitById(id);//방금 조회수 올린거 반영해서 쏴야함
        int recruitViews = recruitDTO.getRecruitViews();
        //관련공고[recruitDTOS4]
        //d.dto에서 관련 MainJobsector 에서 4개의 채용공고 id 띄워주도록 바꾸자?
        String keyword=getFirstWord(recruitDTO.getRecruitMainJobSectors());
        List<Integer> recruitDTOS4 = jobService.get4Recruits(keyword);

        Map<String,Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("recruitDTOdata", recruitDTO);
        response.put("corpDetailIddata", corpDetailDTO);
        response.put("recruitViewsdata", recruitViews);
        response.put("recruitDTOS4data", recruitDTOS4);

        return response;
    }

    //채용 공고 등록 API
    //채용 공고 수정 API
    //채용 공고 삭제 API


    //detail 함수에서 사용하는 첫번째 단어 반환하는 함수
    public String getFirstWord(String input) {
        if (input == null || input.trim().isEmpty()) {
            return ""; // 입력이 null이거나 비어있으면 빈 문자열 반환
        }

        // 문자열을 공백으로 분리
        String[] words = input.split("\\s+"); // 정규 표현식으로 연속된 공백도 처리
        return words[0]; // 첫 번째 단어 반환
    }
}