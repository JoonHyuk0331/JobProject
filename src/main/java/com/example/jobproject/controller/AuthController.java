package com.example.jobproject.controller;

import com.example.jobproject.dto.JoinDTO;
import com.example.jobproject.dto.UserDTO;
import com.example.jobproject.jwt.JWTUtil;
import com.example.jobproject.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthController {

//    로그인 (POST /auth/login)
//    사용자 인증
//    JWT 토큰 발급
//    로그인 이력 저장
//    실패 시 에러 처리
//    토큰 갱신 (POST /auth/refresh)
//    Refresh 토큰 검증
//    새로운 Access 토큰 발급
//    토큰 만료 처리
//    회원 정보 수정 (PUT )

    @Autowired
    private UserService userService ;
    @Autowired
    private JWTUtil jwtUtil ;

    @PostMapping("/auth/register")
    public ResponseEntity<?> joinProcess(UserDTO userDTO) {
        try{
            userService.joinProcess(userDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 회원가입 형식: " + e.getMessage());
        }

        return ResponseEntity.ok("회원가입 성공 :" + userDTO.getUsername());
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> loginProcess(@RequestBody UserDTO userDTO) {
        // 로그인 처리 후 JWT 토큰을 반환하는 로직 (필터에서 수행)
        return ResponseEntity.status(HttpStatus.OK).body("Login successful");
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();//기존 쿠키 배열을 가져온다
        for (Cookie cookie : cookies) {//쿠키중에 refresh 이름의 토큰 (리프레시토큰)이 있는지 찾아서 값 가져오기

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {//없으면 오류출력

            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //expired check 가져온거 만료되었는지 확인
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT 새로운 엑세스코드 발급받기
        String newAccess = jwtUtil.createJwt("access", username, role, 600000L);

        //response
        response.setHeader("access", newAccess);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    //    인증 미들웨어 적용
    //    비밀번호 변경
    //    프로필 정보 수정
    @PutMapping("/auth/profile")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {

    }
}
