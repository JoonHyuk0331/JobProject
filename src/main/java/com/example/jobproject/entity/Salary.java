package com.example.jobproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String salary_company;
    private String salary_corptype;
    private String salary_industy;
    private int salary_avg;
    private int salary_min;
    private int salary_max;
}
