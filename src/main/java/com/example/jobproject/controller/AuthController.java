package com.example.jobproject.controller;

import com.example.jobproject.dto.JoinDTO;
import com.example.jobproject.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

//    회원 가입 (POST /auth/register)
//    이메일 형식 검증
//    비밀번호 암호화 (Base64)
//    중복 회원 검사
//    사용자 정보 저장
//    로그인 (POST /auth/login)
//    사용자 인증
//    JWT 토큰 발급
//    로그인 이력 저장
//    실패 시 에러 처리
//    토큰 갱신 (POST /auth/refresh)
//    Refresh 토큰 검증
//    새로운 Access 토큰 발급
//    토큰 만료 처리
//    회원 정보 수정 (PUT /auth/profile)
//    인증 미들웨어 적용
//    비밀번호 변경
//    프로필 정보 수정
    @Autowired
    private JoinService joinService;

    @PostMapping("/auth/register")
    public ResponseEntity<?> joinProcess(JoinDTO joinDTO) {
        try{
            joinService.joinProcess(joinDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 회원가입 형식: " + e.getMessage());
        }

        return ResponseEntity.ok("회원가입 성공 :" + joinDTO.getUsername());
    }

}
