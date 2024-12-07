package com.example.jobproject.service;

import com.example.jobproject.dto.RecruitDTO;
import com.example.jobproject.entity.Recruit;
import com.example.jobproject.repository.RecruitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private RecruitRepository recruitRepository;

    public Page<RecruitDTO> getListAll(int page){
        Pageable pageable = PageRequest.of(page, 20);//page는 조회할 페이지의 번호이고 20은 한 페이지에 보여 줄 게시물의 개수*(고정)
        Page<Recruit> Recruits = recruitRepository.findAll(pageable);
        Page<RecruitDTO> RecruitDTOs = new RecruitDTO().toRecruitDTOPage(Recruits);
        return RecruitDTOs;
    }
    //2-1.채용 공고 검색 API
    //    키워드 검색
    public Page<RecruitDTO> getListKeyword(String keyword,int page){
        Pageable pageable = PageRequest.of(page, 20);//전체 데이터를 20으로 나눠서 그중 'page'번째를 요청
        Page<Recruit> recruitpage =recruitRepository.findByRecruitTitleContaining(keyword, pageable);
        Page<RecruitDTO> recruitdtopage = new RecruitDTO().toRecruitDTOPage(recruitpage);
        return recruitdtopage;
    }
    //    회사명 검색
    public Page<RecruitDTO> getListCorp(String keyword,int page){
        Pageable pageable = PageRequest.of(page, 20);
        Page<Recruit> recruitpage =recruitRepository.findByRecruitCompanyContaining(keyword, pageable);
        Page<RecruitDTO> recruitdtopage = new RecruitDTO().toRecruitDTOPage(recruitpage);
        return recruitdtopage;
    }
    //    포지션 검색
    public Page<RecruitDTO> getListSector(String keyword,int page){
        Pageable pageable = PageRequest.of(page, 20);
        Page<Recruit> recruitpage =recruitRepository.findByRecruitMainJobSectorsContaining(keyword, pageable);
        Page<RecruitDTO> recruitdtopage = new RecruitDTO().toRecruitDTOPage(recruitpage);
        return recruitdtopage;
    }
    //2-2.채용 공고 필터링 API
    //지역별
    public Page<RecruitDTO> getListLocation(String keyword,int page){
        Pageable pageable = PageRequest.of(page, 20);
        Page<Recruit> recruitpage =recruitRepository.findByRecruitLocationContaining(keyword, pageable);
        Page<RecruitDTO> recruitdtopage = new RecruitDTO().toRecruitDTOPage(recruitpage);
        return recruitdtopage;
    }
    //경력별
    public Page<RecruitDTO> getListExperience(String keyword,int page){
        Pageable pageable = PageRequest.of(page, 20);
        Page<Recruit> recruitpage =recruitRepository.findByRecruitExperienceContaining(keyword, pageable);
        Page<RecruitDTO> recruitdtopage = new RecruitDTO().toRecruitDTOPage(recruitpage);
        return recruitdtopage;
    }
    //급여별
    public Page<RecruitDTO> getListSalary(int money,int page){
        Pageable pageable = PageRequest.of(page, 20);
        Page<Recruit> recruitpage =recruitRepository.findByRecruitSalaryGreaterThanEqual(money, pageable); //money이상의 salary만 반환
        Page<RecruitDTO> recruitdtopage = new RecruitDTO().toRecruitDTOPage(recruitpage);
        return recruitdtopage;
    }
    //기술스택별
    public Page<RecruitDTO> getListJobSector(String keyword,int page){
        Pageable pageable = PageRequest.of(page, 20);
        Page<Recruit> recruitpage =recruitRepository.findByRecruitMainJobSectorsContaining(keyword, pageable);
        Page<RecruitDTO> recruitdtopage = new RecruitDTO().toRecruitDTOPage(recruitpage);
        return recruitdtopage;
    }
    //2-3.공고 상세 조회 API
    public RecruitDTO getRecruitById(int id){
        Optional<Recruit> recruit = recruitRepository.findById(id);
        RecruitDTO recruitDTO = recruit.get().toDTO();
        return recruitDTO;
    }
    //관련공고 4개 id 반환
    public List<Integer> get4Recruits(String keyword){
        Pageable pageable = PageRequest.of(0, 4); // 첫 페이지에서 4개만 가져옴
        Page<Recruit> recruitPage = recruitRepository.findByRecruitMainJobSectorsContaining(keyword, pageable);
        //Page<Recruit> 를 List<Recruit> 로 만들기
        List<Recruit> recruitList = recruitPage.getContent();
        //List<Recruit> 를 List<recruitIdList> 로 만들기
        List<Integer> recruitIdList = new ArrayList<>();
        for (Recruit recruit : recruitList) {
            recruitIdList.add(recruit.toDTO().getId()); // Recruit -> RecruitDTO 변환해서 List에 넣기
        }
        return recruitIdList;
    }
    //조회수 업데이트 서비스
    @Transactional//@Modifying 어노테이션을 사용할 때는 트랜잭션이 필요
    public int updateView(int id){
        return recruitRepository.updateView(id);
    }
}