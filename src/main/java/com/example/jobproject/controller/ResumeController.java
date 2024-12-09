package com.example.jobproject.controller;

import com.example.jobproject.dto.ResumeDTO;
import com.example.jobproject.entity.Resume;
import com.example.jobproject.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/resume")
    public ResponseEntity<String> create(@RequestBody ResumeDTO resumeDTO) {
        resumeService.save(resumeDTO);//resumeDTO만 넘기는데 유저 정보가 같이 들어가나?
        return ResponseEntity.ok("Resume posting save successfully!");
    }

    //이력서 삭제하기
    @DeleteMapping("/resume")
    public ResponseEntity<?> delete(@PathVariable int resume_id) {
        resumeService.delete(resume_id);
        return ResponseEntity.ok("Resume posting delete successfully!");
    }
}
