package com.example.jobproject.jwt;

import com.example.jobproject.dto.CustomUserDetails;
import com.example.jobproject.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.AuthenticationException;

import java.util.Collection;
import java.util.Iterator;


//SecurityFilterChain 클라이언트 요청 body에서 username,pw를 받아 token에 담아서 검증을 위한 AuthenticationManager로 전달
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    // 슬픈사실:
    // 우리의 JWT 프로젝트는 폼 로그인 방식이 아니라
    // SecurityConfig에서 formLogin 방식을 disable 했기 때문에
    // 기본적으로 활성화 되어 있는 해당 필터는 동작하지 않는다.
    // 따라서 로그인을 진행하기 위해서 필터를 직접 커스텀하여 등록해야 한다. 시발...

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager,JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //클라이언트 요청에서 username, password 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        System.out.println(username);

        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//
//        String username = customUserDetails.getUsername();
        //유저정보
        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰생성
        //String token = jwtUtil.createJwt(username, role, 60*60*1900L);//+ 60*60*9L 토큰 생성은 UTF시간이 표준, 한국시차9시간추가해줌
        //첫번째 인자: 토큰이 access토큰인지 refresh토큰인지 구별하는~
        String access = jwtUtil.createJwt("access",username,role,600000L);
        String refresh = jwtUtil.createJwt("refresh",username,role,86400000L);

        //기존:response.addHeader("Authorization", "Bearer " + token);
        response.setHeader("Authorization",access);
        response.addCookie(createCookie("refresh",refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

    public Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);//쿠키의 생명주기
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}