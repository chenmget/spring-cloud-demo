/**
 * Copyright 2010 ZTEsoft Inc. All Rights Reserved.
 * <p>
 * This software is the proprietary information of ZTEsoft Inc.
 * Use is subject to license terms.
 * <p>
 * $Tracker List
 * <p>
 * $TaskId: $ $Date: 9:24:36 AM (May 9, 2008) $comments: create
 * $TaskId: $ $Date: 3:56:36 PM (SEP 13, 2010) $comments: upgrade jvm to jvm1.5
 */
package com.iwhalecloud.retail.oms.exception;

import java.io.Serializable;
import java.util.Date;

/**
 * 异常定义
 */
public final class BaseException extends Exception implements Serializable {

    public static final int INNER_ERROR = 1;
    public static final int BUSS_ERROR = 0;
    private static final long serialVersionUID = -27020729327620212L;
    private int id;

    private String code;

    private String desc;

    private String localeMessage;

    private Date time;

    private int type;

    public BaseException() {
        super();
    }

    public BaseException(String code) {
        this(code, null, INNER_ERROR, null, null, null, null);
    }

    public BaseException(String code, String msg) {
        this(code, msg, INNER_ERROR, null, null, null, null);
    }

    public BaseException(String code, String msg, String arg0) {
        this(code, msg, INNER_ERROR, null, arg0, null, null);
    }

    public BaseException(String code, Throwable cause) {
        this(code, null, INNER_ERROR, cause, null, null, null);
    }

    public BaseException(String code, int errorType, Throwable cause) {
        this(code, null, errorType, cause, null, null, null);
    }

    public BaseException(String code, String msg, int errorType) {
        this(code, msg, errorType, null, null, null, null);
    }

    public BaseException(String code, String param1, Throwable cause) {
        this(code, null, INNER_ERROR, cause, param1, null, null);
    }

    public BaseException(String code, String param1, String param2, Throwable cause) {
        this(code, null, INNER_ERROR, cause, param1, param2, null);
    }

    /**
     * 为了可对异常信息进行参数替换，扩展了String arg0,String arg1,String arg2 三个参数
     *
     * @param errorCode String
     * @param message String
     * @param errorType int
     * @param cause Throwable
     * @param arg0 String
     * @param arg1 String
     * @param arg2 String
     */
    public BaseException(String errorCode, String message, int errorType, Throwable cause, String arg0, String arg1,
                         String arg2) {
        super(message, cause);

        this.code = errorCode;
        this.desc = message;
        BaseException beCause = ExceptionUtil.getFirstBaseException(cause);
        if (beCause == null) {
            this.type = errorType;
        } else {
            this.type = beCause.getType();
        }
    }

    /**
     * get Id
     *
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * set Id
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get Code
     *
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * set Code
     *
     * @param code String
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * get Description
     *
     * @return String
     */
    public String getDesc() {
        return desc;
    }

    /**
     * set Description
     *
     * @param desc String
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * get Time
     *
     * @return Date
     */
    public Date getTime() {
        if (time == null) {
            time = new Date();
        }
        return time;
    }

    /**
     * set Time
     *
     * @param time Date
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * get Type
     *
     * @return int
     */
    public int getType() {
        return type;
    }

    /**
     * set type
     *
     * @param type int
     */
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("errorCode = [");
        sb.append(code);
        sb.append("] errorDesc = [");
        if (localeMessage != null) {
            sb.append(localeMessage);
        }
        sb.append("]");
        if (desc != null) {
            sb.append("  Description = [");
            sb.append(desc);
            sb.append("]");
        }

        return sb.toString();
    }

    public String toStringNonTrace() {
        StringBuilder sb = new StringBuilder();

        sb.append("errorCode = [");
        sb.append(code);
        sb.append("] errorDesc = [");
        if (localeMessage != null) {
            sb.append(localeMessage);
        }
        sb.append("]");
        if (desc != null) {
            sb.append("  Description = [");
            sb.append(desc);
            sb.append("]");
        }

        Throwable cause = getCause();
        if (cause != null) {
            while (true) {
                if (cause.getCause() != null) {
                    cause = cause.getCause();
                } else {
                    break;
                }
            }
        }
        if (cause != null) {
            sb.append(" cause = [");
            sb.append(cause.getClass().getName());
            sb.append(":");
            sb.append(cause.getMessage());
            sb.append("]");
        }
        return sb.toString();
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (message == null) {
            message = getLocaleMessage();
            if (code != null) {
                message = new StringBuilder().append('[').append(code).append("] ").append(message).toString();
            }
        } else if (code != null) {
            message = new StringBuilder().append('[').append(code).append("] ").append(message).toString();
        }
        return message;
    }

    public String getLocaleMessage() {
        return localeMessage;
    }

    public void setLocaleMessage(String localeMessage) {
        this.localeMessage = localeMessage;
    }

    public String getDetailMessage() {
        StringBuilder content = new StringBuilder();
        if (code != null) {
            content.append('[').append(code).append("] ");
        }
        if (localeMessage != null) {
            content.append('[').append(localeMessage.trim()).append("] ");
        }
        String message = super.getMessage();
        if (message != null) {
            content.append('[').append(message.trim()).append("] ");
        }
        return content.toString();
    }
}
