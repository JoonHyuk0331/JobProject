package com.example.jobproject.controller;

import com.example.jobproject.dto.FavoriteRecuritDTO;
import com.example.jobproject.service.BookmarksService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Operation(summary = "북마크", description = "사용자가 특정 id 의 채용공고에 북마크합니다. 한번 더 요청할경우 북마크가 취소됩니다")
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

    @Operation(summary = "북마크목록", description = "현재 로그인한 사용자의 북마크 리스트를 반환합니다")
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
