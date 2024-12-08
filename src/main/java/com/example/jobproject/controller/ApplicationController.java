package com.example.jobproject.controller;

import com.example.jobproject.service.ApplicationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController{
//{c. 지원 관리 API (/applications)
//    1지원하기 (POST /applications)
//    인증 확인
//    중복 지원 체크
//    지원 정보 저장
//    이력서 첨부 (선택)
//    2지원 내역 조회 (GET /applications)
//    사용자별 지원 목록
//    상태별 필터링
//    날짜별 정렬
//    3지원 취소 (DELETE /applications/:id)
//    인증 확인
//    취소 가능 여부 확인
//    상태 업데이트

    @Autowired
    private ApplicationsService applicationsService;

    //지원하기
    @PostMapping("/applications/apply")
    public ResponseEntity<?> apply(int recruitId,int resumeId) {
        try {
            applicationsService.apply(recruitId, resumeId);
            return ResponseEntity.ok("지원성공 ,이력서 성공적으로 제출.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력된 파라미터를 통해 이력서,채용공고를 찾을 수 없거나 현재 로그인된 사용자의 Id가 식별되지 않습니다" + e.getMessage());
        } catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한 오류- 이력서: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("지원 프로세스 중 서버측에서 알 수 없는 오류 발생: " + e.getMessage());
        }
    }
}
