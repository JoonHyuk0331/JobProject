package com.example.jobproject.service;

import com.example.jobproject.dto.ResumeDTO;
import com.example.jobproject.entity.Recruit;
import com.example.jobproject.entity.Resume;
import com.example.jobproject.entity.User;
import com.example.jobproject.repository.ResumeRepository;
import com.example.jobproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ResumeService {
    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private UserRepository userRepository;

    public void save (ResumeDTO resumeDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // API 요청에 들어있는 토큰의 username 정보 (JWT방식이라 토큰으로 로그인유무 판단)
        // **현재 로그인 유저 토큰 따와서 유저 id 자동으로 박아넣기
        // username을 통해 User 엔티티 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        Resume resume = new Resume();
        //id는 GeneratedValue 자동생성
        resume.setTitle(resumeDTO.getTitle());
        resume.setContent(resumeDTO.getContent());
        resume.setUser(user);
        resumeRepository.save(resume);
    }

    public void delete (int resumeId) {
        resumeRepository.deleteById(resumeId);
    }
}
