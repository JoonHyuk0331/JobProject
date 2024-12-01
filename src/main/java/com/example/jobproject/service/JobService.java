package com.example.jobproject.service;

import com.example.jobproject.entity.Recruit;
import com.example.jobproject.repository.RecruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private RecruitRepository recruitRepository;

    public Page<Recruit> getAllRecruits(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return recruitRepository.findAll(pageable);
    }
}