package com.example.jobproject.controller;

import com.example.jobproject.dto.CorpDetailDTO;
import com.example.jobproject.dto.RecruitDTO;
import com.example.jobproject.service.CorpDetailService;
import com.example.jobproject.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class JobController {

    @Autowired
    private JobService jobService;
    @Autowired
    private CorpDetailService corpDetailService;

    //1.채용 공고 조회 API (페이징)-------------------------------------------------------------------------------------
    @Operation(summary = "채용공고 조회", description = "전체 채용공고를 페이징하여 출력합니다 정렬기준과 정렬방식을 선택할 수 있습니다")
    @GetMapping("/jobs")
    public Map<String, Object> recruitList(@Parameter(description = "정렬 기준이 될 필드명을 입력하세요", required = true) @RequestParam(defaultValue = "id") String keyword,
                                           @RequestParam(defaultValue = "0") int page,
                                           @Parameter(description = "오름차순 : ASC 내림차순 : DESC", required = true) @RequestParam(defaultValue = "ASC") String order) {
        Page<RecruitDTO> recruitPage = jobService.getSortedListAll(keyword,page,order);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", recruitPage);  // DTO 리스트로 반환
        return response;
    }

    //2-1.채용 공고 검색 API--------------------------------------------------------------------------------------------
    //    키워드 검색
    @Operation(summary = "키워드 검색",description = "입력한 키워드의 제목을 가진 공고 출력")
    @GetMapping("/jobs/search/keyword")
    public Map<String ,Object> keyword(@RequestParam String keyword,@RequestParam(defaultValue = "0") int page) {
        Page<RecruitDTO> recruitPage = jobService.getListKeyword(keyword,page);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", recruitPage);
        return response;
    }
    //    회사명 검색
    @Operation(summary = "회사명 검색",description = "입력한 키워드의 회사이름을 가진 공고 출력")
    @GetMapping("/jobs/search/corp")
    public Map<String ,Object> corp(@RequestParam String keyword,@RequestParam(defaultValue = "0") int page) {
        Page<RecruitDTO> recruitPage = jobService.getListCorp(keyword,page);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", recruitPage);
        return response;
    }
    //    포지션 검색
    @Operation(summary = "포지션 검색",description = "입력한 키워드의 포지션을 가진 공고 출력")
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
    @Operation(summary = "지역별 필터링",description = "입력한 키워드의 지역에 속한 공고 출력")
    @GetMapping("/jobs/filter/location")
    public Map<String ,Object> filterLocation(@RequestParam String keyword,@RequestParam(defaultValue = "0") int page) {
        Page<RecruitDTO> recruitPage = jobService.getListLocation(keyword,page);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", recruitPage);
        return response;
    }
    //경력별
    @Operation(summary = "경력별 필터링",description = "입력한 키워드의 경력을 요구하는 공고 출력")
    @GetMapping("/jobs/filter/experience")
    public Map<String ,Object> filterExperience(@RequestParam String keyword,@RequestParam(defaultValue = "0") int page) {
        Page<RecruitDTO> recruitPage = jobService.getListExperience(keyword,page);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", recruitPage);
        return response;
    }
    //급여별
    @Operation(summary = "급여별 필터링", description = "입력한 급여값 이상의 정보만 필터링되어 출력됩니다")
    @GetMapping("/jobs/filter/salary")
    public Map<String ,Object> filterSalary(@RequestParam int money,@RequestParam(defaultValue = "0") int page) {
        Page<RecruitDTO> recruitPage = jobService.getListSalary(money,page);//money(단위 만) 이상의 급여 행만 반환
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", recruitPage);
        return response;
    }
    //기술스택
    @Operation(summary = "기술스택별 필터링",description = "입력한 기술스택을 요구하는 정보만 필터링되어 출력됩니다")
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
    @Operation(summary = "공고 상세 조회", description = "입력한 Id값의 상세 공고 정보를 조회합니다")
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
    @Operation(summary = "채용공고 등록", description = "JSON객체를 입력으로 받습니다 회사 Id를 모를경우 corpId 필드를 제외하고 요청을 보내세요 null 값으로 처리됩니다")
    @PostMapping("/jobs/create")
    public ResponseEntity<String> createRecruit (@RequestBody RecruitDTO recruitDTO ) {
        jobService.createRecruit(recruitDTO);
        return ResponseEntity.ok("Recruit posting created successfully!");
    }
    //채용 공고 수정 API
    @Operation(summary = "채용공고 수정", description = "JSON객체를 입력으로 받습니다 id,title,view,corp 필드는 변하지 않으니 제외하고 요청을 보내세요")
    @PostMapping("/jobs/update/{id}")
    public ResponseEntity<String> updateRecruit (@PathVariable int id, @RequestBody RecruitDTO recruitDTO) {
        jobService.updateRecruit(id,recruitDTO);
        return ResponseEntity.ok("Recruit posting updated successfully!");
    }
    //채용 공고 삭제 API
    @Operation(summary = "채용공고 삭제", description = "입력한 id에 해당하는 채용공고를 삭제합니다")
    @DeleteMapping("/jobs/delete/{id}")
    public ResponseEntity<String> deleteRecruit (@PathVariable int id) {
        jobService.deleteRecruit(id);
        return ResponseEntity.ok("Recruit posting deleted successfully!");
    }

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