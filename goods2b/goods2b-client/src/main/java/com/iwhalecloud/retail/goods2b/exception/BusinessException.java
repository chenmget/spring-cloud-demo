package com.iwhalecloud.retail.goods2b.exception;

import java.io.Serializable;

public class BusinessException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -8248090155724252769L;
    protected String errorCode;

    public BusinessException(String message) {
        super(message);

    }
    /**
     * 自定义异常编码和异常提示信息构造业务异常信息
     * @param code 异常编码
     * @param message 异常提示信息
     */
    public BusinessException(String code, String message) {
        super(message);
        this.errorCode = code;
    }

    /**
     * 自定义异常编码和异常提示信息,以及原始异常堆栈构造业务异常信息
     * @param code 异常编码
     * @param message 异常提示信息
     * @param cause 原始异常堆栈
     */
    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = code;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
