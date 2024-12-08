package com.example.jobproject.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//커스텀 글로벌 에러 핸들러

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public DataNotFoundException(String message) {
        super(message);
    }
}