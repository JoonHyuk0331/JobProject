package com.example.jobproject.service;

import com.example.jobproject.dto.JoinDTO;
import com.example.jobproject.entity.User;
import com.example.jobproject.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    //생성자 주입 방식
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        Boolean isExists = userRepository.existsByUsername(username);

        if(isExists) {
            //동일 유저 네임(이메일) 존재하면
            System.out.println("already exists username(Email)");
            return;
        }

        User data = new User();
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));//그냥 안넣고 암호화해서 넣을거임
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }
}
