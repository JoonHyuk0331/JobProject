package com.example.jobproject.controller;

import com.example.jobproject.dto.RecruitDTO;
import com.example.jobproject.dto.SalaryDTO;
import com.example.jobproject.entity.Salary;
import com.example.jobproject.service.SalaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @Operation(summary = "상위회사 연봉정보", description = "상위연봉 회사 연봉정보 출력")
    @GetMapping("/salary")
    public Map<String, Object> TopSalaryList(@RequestParam(defaultValue = "0") int page) {
        Page<SalaryDTO> salaryPage = salaryService.getListAll(page);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", salaryPage);  // DTO 리스트로 반환
        return response;
    }
}
