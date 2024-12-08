package com.example.jobproject.service;

import com.example.jobproject.CustomException.DataNotFoundException;
import com.example.jobproject.dto.RecruitDTO;
import com.example.jobproject.entity.Corp;
import com.example.jobproject.entity.Recruit;
import com.example.jobproject.repository.CorpRepository;
import com.example.jobproject.repository.RecruitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private RecruitRepository recruitRepository;
    @Autowired
    private CorpRepository corpRepository;

    public Page<RecruitDTO> getListAll(int page){
        Pageable pageable = PageRequest.of(page, 20);//page는 조회할 페이지의 번호이고 20은 한 페이지에 보여 줄 게시물의 개수*(고정)
        Page<Recruit> Recruits = recruitRepository.findAll(pageable);
        Page<RecruitDTO> RecruitDTOs = new RecruitDTO().toRecruitDTOPage(Recruits);
        return RecruitDTOs;
    }

    public Page<RecruitDTO> getSortedListAll(String sortKeyword ,int page, String order){//sortKeyword명은 반드시 엔티티의 필드명과 일치해야 정상적으로 정렬
        Sort.Direction direction = "ASC".equalsIgnoreCase(order) ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, 20, Sort.by(direction, sortKeyword));
        Page<Recruit> recruits = recruitRepository.findAll(pageable);
        Page<RecruitDTO> recruitDTOs = new RecruitDTO().toRecruitDTOPage(recruits);

        return recruitDTOs;
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
    //2-3
    // 채용 공고 등록 API
    public void createRecruit(RecruitDTO recruitDTO) {
        Corp corp = null; // null 허용, 회사id 없는 JSON 전달받으면 null값으로 집어넣기

        // corpId가 존재하는 경우에만 corp 조회
        if (recruitDTO.getCorpId() != null) {
            corp = corpRepository.findById(recruitDTO.getCorpId())
                    .orElseThrow(() -> new DataNotFoundException("Company not found"));
        }

        // Recruit 객체 생성 및 값 설정
        Recruit recruit = recruitDTO.toEntity();
        recruit.setCorp(corp); // recruitDTO.toEntity();에서 corp객체는 null 값으로 가져오니 corp 설정은 따로 해주기
        // 저장
        recruitRepository.save(recruit);

    }
    // 채용 공고 수정 API
    //방식: 입력받은 id로 수정할 대상이 되는 기존 Recruit 찾아와서
    //입력받은 DTO에서 id를 제외하고 수정할 부분만 따로 조작 후 save
    public void updateRecruit(int id,RecruitDTO recruitDTO) {
        Recruit recruit = recruitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post ID: " + id));

        //수정 id,recruitTitle,recruitSalary,corp 를 제외한 값
        recruit.setRecruitCompany(recruitDTO.getRecruitCompany());
        recruit.setRecruitLocation(recruitDTO.getRecruitLocation());
        recruit.setRecruitExperience(recruitDTO.getRecruitExperience());
        recruit.setRecruitJobType(recruitDTO.getRecruitJobType());
        recruit.setRecruitDeadline(recruitDTO.getRecruitDeadline());
        recruit.setRecruitRequirement(recruitDTO.getRecruitRequirement());
        recruit.setRecruitMainJobSectors(recruitDTO.getRecruitMainJobSectors());
        recruit.setRecruitSideJobSectors(recruitDTO.getRecruitSideJobSectors());
        recruit.setRecruitSalary(recruitDTO.getRecruitSalary());

        recruitRepository.save(recruit);
    }
    // 채용 공고 삭제 API
    public void deleteRecruit(int id) {
        recruitRepository.deleteById(id);
    }
}