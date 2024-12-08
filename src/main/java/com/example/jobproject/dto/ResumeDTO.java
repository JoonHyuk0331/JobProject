package com.example.jobproject.dto;

import com.example.jobproject.entity.Resume;
import com.example.jobproject.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResumeDTO {
    public ResumeDTO() {}
    private int id;            // 이력서 ID
    private String title;      // 이력서 제목
    private String content;    // 이력서 내용
    private int userId;        // 이력서를 소유한 사용자의 ID

    public Resume toEntity(User user) {//이렇게 매개변수로 받아서 user 내용 채울수도 있을듯
        Resume resume = new Resume();
        resume.setId(id);
        resume.setTitle(title);
        resume.setContent(content);
        resume.setUser(user);            // User 설정
        return resume;
    }
}
