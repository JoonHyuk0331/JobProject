package com.example.jobproject.dto;

import com.example.jobproject.entity.CorpDetail;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorpDetailDTO {

    public CorpDetailDTO(){}
    private int id;
    private String mostHiredPositionsRanking;
    private int averageSalary;
    private String revenue;
    private String operatingProfit;
    private String netProfit;
    private String capital;

    //entity => DTO 변환클래스
    public CorpDetailDTO(CorpDetail corpDetail) {
        this.id= corpDetail.getId();
        this.mostHiredPositionsRanking= corpDetail.getMostHiredPositionsRanking();
        this.averageSalary= corpDetail.getAverageSalary();
        this.revenue= corpDetail.getRevenue();
        this.operatingProfit= corpDetail.getOperatingProfit();
        this.netProfit= corpDetail.getNetProfit();
        this.capital= corpDetail.getCapital();
    }
}
