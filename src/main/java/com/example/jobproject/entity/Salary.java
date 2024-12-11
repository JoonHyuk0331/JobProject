package com.example.jobproject.entity;

import com.example.jobproject.dto.SalaryDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "salary_company")
    private String salaryCompany;

    @Column(name = "salary_corptype")
    private String salaryCorpType;

    @Column(name = "salary_industy")
    private String salaryIndustry;

    @Column(name = "salary_avg")
    private int salaryAvg;

    @Column(name = "salary_min")
    private int salaryMin;

    @Column(name = "salary_max")
    private int salaryMax;

    public SalaryDTO toDTO(){
        SalaryDTO salaryDTO = new SalaryDTO();
        salaryDTO.setId(id);
        salaryDTO.setSalaryCompany(salaryCompany);
        salaryDTO.setSalaryCorpType(salaryCorpType);
        salaryDTO.setSalaryIndustry(salaryIndustry);
        salaryDTO.setSalaryAvg(salaryAvg);
        salaryDTO.setSalaryMin(salaryMin);
        salaryDTO.setSalaryMax(salaryMax);
        return salaryDTO;
    }
}
