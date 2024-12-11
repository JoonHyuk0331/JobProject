package com.example.jobproject.service;

import com.example.jobproject.dto.FavoriteRecuritDTO;
import com.example.jobproject.dto.RecruitDTO;
import com.example.jobproject.entity.FavoriteRecruit;
import com.example.jobproject.entity.Recruit;
import com.example.jobproject.entity.User;
import com.example.jobproject.exception.DataNotFoundException;
import com.example.jobproject.repository.FavoriteRecruitRepository;
import com.example.jobproject.repository.RecruitRepository;
import com.example.jobproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookmarksService {

    @Autowired
    private RecruitRepository recruitRepository;
    @Autowired
    private FavoriteRecruitRepository favoriteRecruitRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String toggleBookmark(int resume) {
        // 로그인된 사용자의 username 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // API 요청에 들어있는 토큰의 username 정보 (JWT방식이라 토큰으로 로그인유무 판단)
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("해당 유저를 찾을 수 없습니다."));
        // 이력서 아이디
        Recruit recruit = recruitRepository.findById(resume)
                .orElseThrow(() -> new DataNotFoundException("resume not found"));

        // 기존에 북마크가 존재하는지 확인
        Optional<FavoriteRecruit> existingBookmark = favoriteRecruitRepository.findByUserAndRecruit(user, recruit);
        if (existingBookmark.isPresent()) {
            FavoriteRecruit favoriteRecruit = existingBookmark.get();
            if (favoriteRecruit.isBookmarked()){
                favoriteRecruit.setBookmarked(false);
            } else {
                favoriteRecruit.setBookmarked(true);
            }
            favoriteRecruitRepository.save(existingBookmark.get());
            return favoriteRecruit.isBookmarked() ? "북마크 추가됨" : "북마크 해제됨";
        }else{
            FavoriteRecruit favoriteRecruit = new FavoriteRecruit();
            favoriteRecruit.setUser(user);
            favoriteRecruit.setBookmarked(true);
            favoriteRecruit.setRecruit(recruit);
            favoriteRecruit.setCreateDate(LocalDateTime.now());
            favoriteRecruitRepository.save(favoriteRecruit);
            return "북마크 추가됨";
        }
    }

    public List<FavoriteRecuritDTO> getBookmarkList(int page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // API 요청에 들어있는 토큰의 username 정보 (JWT방식이라 토큰으로 로그인유무 판단)
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("해당 유저를 찾을 수 없습니다."));

        Pageable pageable = PageRequest.of(page, 20,Sort.Direction.ASC, "create_date");//생성일자 순으로 오름차순
        Page<FavoriteRecruit> favoriteRecruits = favoriteRecruitRepository.findByUser(user,pageable);

        List<FavoriteRecuritDTO> favoriteRecuritDTOList = new ArrayList<>();
        for(FavoriteRecruit favoriteRecruit : favoriteRecruits){
            FavoriteRecuritDTO favoriteRecuritDTO = new FavoriteRecuritDTO();
            favoriteRecuritDTO.setId(favoriteRecruit.getId());
            favoriteRecuritDTO.setRecruit_id(favoriteRecruit.getRecruit().getId());
            favoriteRecuritDTO.setUser_id(favoriteRecruit.getUser().getId());
            favoriteRecuritDTO.setBookmarked(favoriteRecruit.isBookmarked());
            favoriteRecuritDTOList.add(favoriteRecuritDTO);
        }
        return favoriteRecuritDTOList;
    }
}
