package com.example.jobproject.service;

import com.example.jobproject.entity.Corp;
import com.example.jobproject.repository.CorpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorpService {
    @Autowired
    private CorpRepository corpRepository;
    //회사이름에 해당하는 pk가져옴
    public int getPK(String corpName){
        Corp corp=corpRepository.findByCorpTitle(corpName);
        int corp_id=corp.getId();
        return corp_id;
    }
}
