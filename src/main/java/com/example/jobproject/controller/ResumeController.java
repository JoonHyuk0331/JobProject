package com.example.jobproject.controller;

import com.example.jobproject.dto.ResumeDTO;
import com.example.jobproject.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Operation(summary = "이력서 저장", description = "이력서 저장")
    @PostMapping("/resume")
    public ResponseEntity<String> create(@RequestBody ResumeDTO resumeDTO) {
        resumeService.save(resumeDTO);//resumeDTO만 넘기는데 유저 정보가 같이 들어가나?
        return ResponseEntity.ok("Resume posting save successfully!");
    }

    //이력서 삭제하기
    @Operation(summary = "이력서 삭제", description = "입력받은 id의 이력서를 삭제합니다")
    @DeleteMapping("/resume/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        resumeService.delete(id);
        return ResponseEntity.ok("Resume posting delete successfully!");
    }
}
