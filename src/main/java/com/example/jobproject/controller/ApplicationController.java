package com.example.jobproject.controller;
import com.example.jobproject.service.ApplicationsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApplicationController{

//    2지원 내역 조회 (GET /applications)
//    사용자별 지원 목록
//    상태별 필터링
//    날짜별 정렬
//    3지원 취소 (DELETE /applications/:id)
//    인증 확인
//    상태 업데이트

    @Autowired
    private ApplicationsService applicationsService;

    //지원하기
    @Operation(summary = "지원하기", description = "지원하려는 공고의 Id , 첨부할 이력서의 Id 가 필요합니다")
    @PostMapping("/applications")
    public ResponseEntity<?> apply(int recruitId,int resumeId) {
        applicationsService.apply(recruitId, resumeId);
        return ResponseEntity.ok("지원성공 ,이력서 성공적으로 제출.");
    }

    //지원취소
    @Operation(summary = "지원취소", description = "취소할 지원내역의 Id를 입력해주세요")
    @DeleteMapping("/applications/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        applicationsService.delete(id);
        return ResponseEntity.ok("지원취소 성공, 해당 Id 에 해당하는 지원 내역 삭제");
    }

    //지원조회
    @Operation(summary = "지원조회", description = "현재 로그인된 사용자가 지원한 공고의 제목 리스트들을 반환")
    @GetMapping("/applications")
    public Map<String, Object> getAllList() {
        // todo : //    상태별 필터링,날짜별 정렬 구체적으로 뭘 말하는거지?
        List<String> appliedTitleList = applicationsService.getAllApplyRecruitList();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", appliedTitleList);
        return response;
    }
}
