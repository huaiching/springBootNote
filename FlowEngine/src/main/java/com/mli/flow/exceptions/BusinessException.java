package com.mli.flow.exceptions;

import org.springframework.http.HttpStatus;

/**
 * 自定義業務異常
 */

public class BusinessException extends RuntimeException {
    private final Integer code;
    private final String message;
    private final HttpStatus status;

    public BusinessException(HttpStatus status, String message) {
        super(message);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public BusinessException(Integer code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
