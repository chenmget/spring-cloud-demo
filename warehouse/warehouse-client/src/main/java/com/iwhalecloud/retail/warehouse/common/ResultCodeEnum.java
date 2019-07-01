package com.iwhalecloud.retail.warehouse.common;

import com.iwhalecloud.retail.dto.ResultCode;

/**
 * 返回值枚举类
 *
 * @author mzl
 * @date 2018/9/30
 */
public enum ResultCodeEnum implements ResultCode {

    SUCCESS("0","SUCCESS"),
    ERROR("-1","ERROR"),
    QUERY_DB_EXCEPTION("-7","QUERY_DB_EXCEPTION"),
    FORBID_UPLOAD_ERROR("-10", "非法的附件上传类型");

    private final String code;
    private final String desc;


    ResultCodeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }

}
