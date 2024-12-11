package com.example.jobproject.service;

import com.example.jobproject.exception.DataDuplicateException;
import com.example.jobproject.entity.ApplyList;
import com.example.jobproject.entity.Recruit;
import com.example.jobproject.entity.Resume;
import com.example.jobproject.entity.User;
import com.example.jobproject.exception.DataNotFoundException;
import com.example.jobproject.exception.DeadlinePassedException;
import com.example.jobproject.repository.ApplyListRepository;
import com.example.jobproject.repository.RecruitRepository;
import com.example.jobproject.repository.ResumeRepository;
import com.example.jobproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    //유저가 -> 채용공고 지원
    public void apply(int recruitId,int resumeId){//
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // API 요청에 들어있는 토큰의 username 정보 (JWT방식이라 토큰으로 로그인유무 판단)
        // username을 통해 User 엔티티 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("해당 유저를 찾을 수 없습니다."));
        // 이력서 엔티티 조회
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new DataNotFoundException("해당 이력서를 찾을 수 없습니다."));
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
                .orElseThrow(() -> new DataNotFoundException("해당 채용 공고를 찾을 수 없습니다."));


        // 유저가 해당 채용공고에 이미 지원했는지 확인
        List<ApplyList> userapplyLists = user.getApplyList();
        for(ApplyList applyList : userapplyLists){
            if (applyList.getId() == recruitId) {
                throw new DataNotFoundException("현재 로그인한 사용자는 이미 해당 공고에 지원했습니다.");
            }
        }

        //마감된 공고는 취소(삭제) 불가
        if (LocalDateTime.now().isAfter(recruit.getRecruitDeadline())) {
            throw new DeadlinePassedException("마감된 공고, 지원 가능한 기간이 만료되었습니다.");
        }

        // ApplyList 객체 생성 및 데이터 설정
        ApplyList applyList = new ApplyList();
        applyList.setResume(resume);    // 지정한 이력서
        applyList.setUser(user);        // 현재 로그인한 사용자
        applyList.setRecruit(recruit);  // 지원할 채용 공고
        applyList.setCreateDate(LocalDateTime.now());//지원한 시간

        // 연결 테이블 저장
        applyListRepository.save(applyList);
    }

    //삭제
    public void delete(int applyId){
        ApplyList applyList = applyListRepository.findById(applyId)
                .orElseThrow(() -> new DataNotFoundException("해당 채용 공고를 찾을 수 없습니다."));
        Recruit recruit = recruitRepository.findById(applyList.getRecruit().getId())
                .orElseThrow(() -> new DataNotFoundException("해당 채용 공고를 찾을 수 없습니다."));

        //마감된 공고는 취소(삭제) 불가
        if (LocalDateTime.now().isAfter(recruit.getRecruitDeadline())) {
            throw new DeadlinePassedException("마감된 공고, 취소 가능한 기간이 만료되었습니다.");
        }
        applyListRepository.deleteById(applyId);
    }

    //지원내역 조회 :
    public List<String> getAllApplyRecruitList(){
        List<String> list = new ArrayList<>();

        // 로그인된 사용자의 username 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // API 요청에 들어있는 토큰의 username 정보 (JWT방식이라 토큰으로 로그인유무 판단)
        // username을 통해 User 엔티티 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("해당 유저를 찾을 수 없습니다."));
        List<ApplyList> userApplyLists = user.getApplyList()
                .stream()
                .sorted(Comparator.comparing(ApplyList::getCreateDate)) // createDate 기준 오름차순 정렬
                .toList();
        for(ApplyList applyList : userApplyLists){
            String title=applyList.getRecruit().getRecruitTitle();
            list.add(title);
        }
        return list;
    }
}
