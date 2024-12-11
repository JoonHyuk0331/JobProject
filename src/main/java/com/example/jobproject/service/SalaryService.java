package com.example.jobproject.service;

import com.example.jobproject.dto.SalaryDTO;
import com.example.jobproject.entity.Salary;
import com.example.jobproject.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {
    @Autowired
    private SalaryRepository salaryRepository;

    public Page<SalaryDTO> getListAll(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<Salary> salaryPage = salaryRepository.findAll(pageable);
        return salaryPage.map(SalaryDTO::new);
    }
}
