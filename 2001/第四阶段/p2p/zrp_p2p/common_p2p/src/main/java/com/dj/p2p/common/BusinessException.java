package com.dj.p2p.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class BusinessException extends RuntimeException implements Serializable {

    private String errorMsg;

    private Integer errorCode;

    public BusinessException() {}

    public BusinessException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public BusinessException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(Integer errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
