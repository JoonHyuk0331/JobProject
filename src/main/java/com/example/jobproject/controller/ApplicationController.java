package com.example.jobproject.controller;

import com.example.jobproject.CustomException.DataDuplicateException;
import com.example.jobproject.dto.RecruitDTO;
import com.example.jobproject.entity.Recruit;
import com.example.jobproject.service.ApplicationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
//    todo : 취소 가능 여부 확인
//    상태 업데이트

    @Autowired
    private ApplicationsService applicationsService;

    //지원하기
    @PostMapping("/applications")
    public ResponseEntity<?> apply(int recruitId,int resumeId) {
        try {
            applicationsService.apply(recruitId, resumeId);
            return ResponseEntity.ok("지원성공 ,이력서 성공적으로 제출.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력된 파라미터를 통해 이력서,채용공고를 찾을 수 없거나 현재 로그인된 사용자의 Id가 식별되지 않습니다" + e.getMessage());
        } catch (DataDuplicateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("동일 채용공고에 중복해서 지원할 수 없습니다: " + e.getMessage());
        } catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한 오류- 이력서: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("지원 프로세스 중 서버측에서 알 수 없는 오류 발생: " + e.getMessage());
        }
    }

    //지원취소
    @DeleteMapping("/applications/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try{
            applicationsService.delete(id);
            return ResponseEntity.ok("지원취소 성공, 해당 Id 에 해당하는 지원 내역 삭제");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("지원취소 프로세스 중 서버측에서 알 수 없는 오류 발생: " + e.getMessage());
        }
    }

    //지원조회
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
