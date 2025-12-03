package com.mli.flow.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 全域異常處理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 處理自訂 BusinessException
     *
     * - 捕捉 Service 或 Controller 中丟出的 BusinessException - 將錯誤訊息與 HTTP 狀態碼封裝成
     * Map，統一返回給前端
     *
     * @param exception 捕捉到的 BusinessException
     * @return ResponseEntity<Map<String, Object>> 統一格式的錯誤 JSON
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(BusinessException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", exception.getStatus());
        body.put("message", exception.getMessage());
        body.put("errorCode", exception.getStatus().value());
        body.put("errorMessage", exception.getMessage());
        body.put("timestamp", new Date());
        return ResponseEntity.status(exception.getStatus()).body(body);
    }

    /**
     * 處理所有未捕捉的 Exception
     *
     * <p>
     * 功能： 1. 捕捉專案中未處理的其他 Exception，避免返回無意義的錯誤訊息 2. 統一返回 500 Internal Server Error
     * 給前端
     *
     * @param exception 捕捉到的 Exception
     * @return ResponseEntity<Map<String, Object>> 統一格式的錯誤 JSON
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOtherExceptions(Exception exception) {
        exception.printStackTrace();
        Map<String, Object> body = new HashMap<>();
        body.put("status", 500);
        body.put("message", "Internal Server Error");
        body.put("errorCode", 500);
        body.put("errorMessage", exception.getMessage());
        body.put("timestamp", new Date());
        return ResponseEntity.status(500).body(body);
    }


}