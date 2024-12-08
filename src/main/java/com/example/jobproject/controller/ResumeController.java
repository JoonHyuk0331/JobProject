package com.example.jobproject.controller;

import com.example.jobproject.dto.ResumeDTO;
import com.example.jobproject.entity.Resume;
import com.example.jobproject.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/resume/create")
    public ResponseEntity<String> create(@RequestBody ResumeDTO resumeDTO) {
        resumeService.save(resumeDTO);//resumeDTO만 넘기는데 유저 정보가 같이 들어가나?
        return ResponseEntity.ok("Resume posting save successfully!");
    }


    //이력서 삭제하기

    //이력서 수정은 안만들래 그냥 삭제하고 다시 만들어라;
}
