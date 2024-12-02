package com.example.jobproject.controller;

import com.example.jobproject.dto.RecruitDTO;
import com.example.jobproject.entity.Recruit;
import com.example.jobproject.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/jobs")
    public Map<String, Object> recruitList(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        Page<Recruit> recruitPage = jobService.getList(page, size);

        // Recruit 엔티티를 RecruitDTO로 변환
        List<RecruitDTO> recruitDTOs = recruitPage.getContent().stream()
                .map(Recruit::toDTO)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", recruitDTOs);  // DTO 리스트로 반환

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", recruitPage.getNumber() + 1); // 페이지 번호는 0부터 시작하므로 +1
        pagination.put("totalPages", recruitPage.getTotalPages());
        pagination.put("totalItems", recruitPage.getTotalElements());

        response.put("pagination", pagination);
        return response;
    }

}