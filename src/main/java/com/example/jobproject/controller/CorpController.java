package com.example.jobproject.controller;

import com.example.jobproject.service.CorpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CorpController {

    @Autowired
    private CorpService corpService;

    @GetMapping("/corp/{corp_name}")
    public ResponseEntity<Integer> getCorpId(@PathVariable String corp_name) {
        int corp_id = corpService.getPK(corp_name);
        return ResponseEntity.ok(corp_id);  // 200 OK 상태와 함께 corp_id 반환
    }
}
