package com.kh.myProduct.web.exception;

import lombok.Getter;

@Getter
public class RestBizException extends RuntimeException{ //exception은 try catch문 작성 필요
    private String code;

    public RestBizException() {
        super();
    }

    public RestBizException(String message) {
        super(message);
    }

    public RestBizException(String message, String code) {
        super(message);
        this.code = code;
    }

    public RestBizException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestBizException(Throwable cause) {
        super(cause);
    }

    protected RestBizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}