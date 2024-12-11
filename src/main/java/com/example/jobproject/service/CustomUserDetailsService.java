package com.example.jobproject.service;

import com.example.jobproject.dto.CustomUserDetails;
import com.example.jobproject.entity.User;
import com.example.jobproject.exception.DataNotFoundException;
import com.example.jobproject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //DB에서 조회
        User userData = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("DB에서 해당 유저를 찾을 수 없습니다."));;

        if (userData != null) {

            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(userData);
        }

        return null;
    }
}
