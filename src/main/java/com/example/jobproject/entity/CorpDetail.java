package com.example.jobproject.entity;

import com.example.jobproject.dto.CorpDetailDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CorpDetail {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "corp_detail_id")
    private int id;

    // 가장 많이 채용한 직무별 순위
    @Column(name = "most_hired_positions_ranking")
    private String mostHiredPositionsRanking;

    // 평균 연봉
    @Column(name = "average_salary")
    private int averageSalary;

    // 매출액
    @Column(name = "revenue")
    private String revenue;

    // 영업이익
    @Column(name = "operating_profit")
    private String operatingProfit;

    // 단기 순이익
    @Column(name = "net_profit")
    private String netProfit;

    // 자본금
    @Column(name = "capital")
    private String capital;


    public CorpDetailDTO toDTO() {
        CorpDetailDTO corpDetailDTO = new CorpDetailDTO();
        corpDetailDTO.setId(id);
        corpDetailDTO.setMostHiredPositionsRanking(mostHiredPositionsRanking);
        corpDetailDTO.setAverageSalary(averageSalary);
        corpDetailDTO.setRevenue(revenue);
        corpDetailDTO.setOperatingProfit(operatingProfit);
        corpDetailDTO.setNetProfit(netProfit);
        corpDetailDTO.setCapital(capital);
        return corpDetailDTO;
    }
}
