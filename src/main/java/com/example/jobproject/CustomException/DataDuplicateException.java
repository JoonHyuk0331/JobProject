package com.example.jobproject.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT  , reason = "중복")
public class DataDuplicateException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public DataDuplicateException(String message) {
        super(message);
    }
}