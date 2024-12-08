package com.example.jobproject.service;

import com.example.jobproject.entity.ApplyList;
import com.example.jobproject.entity.Recruit;
import com.example.jobproject.entity.Resume;
import com.example.jobproject.entity.User;
import com.example.jobproject.repository.ApplyListRepository;
import com.example.jobproject.repository.RecruitRepository;
import com.example.jobproject.repository.ResumeRepository;
import com.example.jobproject.repository.UserRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationsService {

    @Autowired
    private ApplyListRepository applyListRepository;
    @Autowired
    private RecruitRepository recruitRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResumeRepository resumeRepository;

    public void apply(int recruitId,int resumeId){//
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // API 요청에 들어있는 토큰의 username 정보 (JWT방식이라 토큰으로 로그인유무 판단)
        // username을 통해 User 엔티티 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        // 이력서 엔티티 조회
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이력서를 찾을 수 없습니다."));
        // 가져온 이력서가 현재 로그인 사용자가 소유한 이력서인지 조회
        boolean isOwnedByUser = false;
        for (Resume r : user.getResumes()) {
            if (r.getId() == resumeId) {
                isOwnedByUser = true;
                break;
            }
        }
        if (!isOwnedByUser) {
            throw new AccessDeniedException("현재 로그인한 사용자는 해당 이력서를 소유하고 있지 않습니다.");
        }

        // 채용 공고 엔티티 조회
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> new IllegalArgumentException("해당 채용 공고를 찾을 수 없습니다."));

        // ApplyList 객체 생성 및 데이터 설정
        ApplyList applyList = new ApplyList();
        applyList.setResume(resume);    // 지정한 이력서
        applyList.setUser(user);        // 현재 로그인한 사용자
        applyList.setRecruit(recruit);  // 지원할 채용 공고

        // 연결 테이블 저장
        applyListRepository.save(applyList);
    }
}
