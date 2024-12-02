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

    public Page<Recruit> getList(int page,int size){
        //Page<Recruit> findAll (Pageable pageable);
        Pageable pageable = PageRequest.of(page, size);//page는 조회할 페이지의 번호이고 10은 한 페이지에 보여 줄 게시물의 개수
        return this.recruitRepository.findAll(pageable);
    }
}