package com.example.jobproject.controller;

import com.example.jobproject.dto.FavoriteRecuritDTO;
import com.example.jobproject.dto.RecruitDTO;
import com.example.jobproject.service.BookmarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BookmarksController {
    @Autowired
    private BookmarksService bookmarksService;

    //사용자가 특정 id 의 북마크에 토글 명령 내림
    @PostMapping("/bookmarks")
    public ResponseEntity<?> toggleBookMarks(int recuitId){
        String responsemsg="";
        try {
            responsemsg=bookmarksService.toggleBookmark(recuitId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("오류 발생: " + e.getMessage());
        }
        return ResponseEntity.ok("toggle successfully: " + responsemsg);
    }

    @GetMapping("/bookmarks")
    public Map<String, Object> getBookmarks(@RequestParam(defaultValue = "0") int page){
        //사용자별 북마크 리스트 쭉 긁어오기
        List<FavoriteRecuritDTO> dtoList= bookmarksService.getBookmarkList(page);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", dtoList);  // DTO 리스트를 반환
        return response;
    }
}
