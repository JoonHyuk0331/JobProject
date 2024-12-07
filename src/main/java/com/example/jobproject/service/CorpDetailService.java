package com.example.jobproject.service;

import com.example.jobproject.CustomException.DataNotFoundException;
import com.example.jobproject.dto.CorpDetailDTO;
import com.example.jobproject.entity.CorpDetail;
import com.example.jobproject.repository.CorpDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CorpDetailService {

    private final CorpDetailRepository corpDetailRepository;

    public CorpDetailService(CorpDetailRepository corpDetailRepository) {
        this.corpDetailRepository = corpDetailRepository;
    }

    public CorpDetailDTO getCorpDetailById(int id) {
        Optional<CorpDetail> corpDetail = corpDetailRepository.findById(id);
        if (corpDetail.isPresent()) {
            return corpDetail.get().toDTO();
        }
        else{
            throw new DataNotFoundException("getCorpDetailById not found");
        }
    }
}
