package com.example.jobproject.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class); // 로거

    // 400 Bad Request 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleBadRequest(IllegalArgumentException ex) {
        logger.error("BadRequestException: {}", ex.getMessage(), ex); // 에러 로그
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 400 Bad Request : 기한만료 예외처리
    @ExceptionHandler(DeadlinePassedException.class)
    public ResponseEntity<String> handleDeadlinePassedException(DeadlinePassedException ex) {
        logger.error("DeadlinePassedException: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // 404 Not Found 처리
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> handleNotFound(DataNotFoundException ex) {
        logger.warn("DataNotFoundException: {}", ex.getMessage()); // 경고 로그
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 409 Conflict 처리
    @ExceptionHandler(DataDuplicateException.class)
    public ResponseEntity<?> handleConflict(DataDuplicateException ex) {
        logger.warn("DataDuplicateException: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    // 403 Forbidden 처리
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
        logger.error("AccessDeniedException: {}", ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    // 500 Internal Server Error 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        logger.error("InternalServerError: {}", ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 알수없는 오류가 발생했습니다.");
    }

    // 공통 에러 응답 빌더
    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("errorCode", status.value());
        response.put("message", message);
        response.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(response, status);
    }
}
