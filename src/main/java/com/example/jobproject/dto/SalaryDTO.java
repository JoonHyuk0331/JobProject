package com.example.jobproject.dto;

import com.example.jobproject.entity.Salary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryDTO {
    public SalaryDTO(){}
    public SalaryDTO(Salary salary) {
        this.id = salary.getId();
        this.salaryCompany = salary.getSalaryCompany();
        this.salaryCorpType = salary.getSalaryCorpType();
        this.salaryIndustry = salary.getSalaryIndustry();
        this.salaryAvg = salary.getSalaryAvg();
        this.salaryMin = salary.getSalaryMin();
        this.salaryMax = salary.getSalaryMax();
    }

    private int id;
    private String salaryCompany;
    private String salaryCorpType;
    private String salaryIndustry;
    private int salaryAvg;
    private int salaryMin;
    private int salaryMax;


}
