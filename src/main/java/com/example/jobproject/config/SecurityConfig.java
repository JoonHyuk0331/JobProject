package com.example.jobproject.config;

import com.example.jobproject.jwt.JWTUtil;
import com.example.jobproject.jwt.JWTfilter;
import com.example.jobproject.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration//securtiy를 위한 config
@EnableWebSecurity
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    //BCrypt 해시 함수를 사용하기 위한 Bean 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf disable : JWT방식은 서버에 세션 정보를 저장하지 않아서 csrf 공격을 막을 필요성 없음
        http
                .csrf((auth) -> auth.disable());

        //From 로그인 방식 disable : HTTP 요청 헤더에 토큰을 담아 전송하므로, 별도의 로그인 페이지나 폼 로그인 방식이 필요하지 않습니다.
        http
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable : 세션인증 방식때, HTTP 요청의 헤더에 사용자 이름과 비밀번호를 Base64로 인코딩해서 전송하는 방식인데 토큰을 보내는 방식이라 사용x
        http
                .httpBasic((auth) -> auth.disable());

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join","/jobs").permitAll()//이 경로들은 인증 없이 누구나 접근할 수 있음
                        .requestMatchers("/admin").hasRole("ADMIN")//admin 경로는 ADMIN 권한을 가진 사용자만 접근할 수 있음
                        .requestMatchers("/main").hasRole("USER")
                        .anyRequest().authenticated());//위에서 명시한 경로 외의 모든 요청은 인증된 사용자만 접근할 수 있도록

        //세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .addFilterAt(new JWTfilter(jwtUtil), LoginFilter.class);
        //커스텀 필터 적용
        // LoginFilter내부 인자를 위해 위에 생성자
        // AuthenticationConfiguration,AuthenticationManager 두개 만들어져있음
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil), UsernamePasswordAuthenticationFilter.class);

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }
}
