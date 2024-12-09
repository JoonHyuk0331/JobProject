package com.example.jobproject.service;

import com.example.jobproject.dto.UserDTO;
import com.example.jobproject.entity.User;
import com.example.jobproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        if(!isValidEmail(username)){
            //   이메일 형식 검증
            System.out.println("이메일 형식에 맞지 않음(Email)");
            throw new IllegalArgumentException("이메일 형식에 맞지 않음.");
        }

        Boolean isExists = userRepository.existsByUsername(username);//for문 안돌리고 이렇게 해도 되겠네
        if(isExists) {
            //동일 유저 네임(이메일) 존재하면
            System.out.println("already exists username(Email)");
            throw new IllegalArgumentException("이미 존재하는 이메일(username).");
        }

        User data = new User();
        data.setUsername(username);
        //    비밀번호 암호화 (Base64)
        data.setPassword(bCryptPasswordEncoder.encode(password));//그냥 안넣고 암호화해서 넣을거임
        data.setRole("ROLE_ADMIN");
        data.setEducation(userDTO.getEducation());
        data.setLocation(userDTO.getLocation());

        userRepository.save(data);
    }

    private static final String EMAIL_REGEX =
            "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
