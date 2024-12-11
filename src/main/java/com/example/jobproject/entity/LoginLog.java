package com.example.jobproject.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "login_logs")
public class LoginLog {
    public LoginLog() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String status; // 성공,실패
    private LocalDateTime timestamp;

    public LoginLog(String username, String status) {
        this.username = username;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}