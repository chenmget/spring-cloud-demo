package com.iwhalecloud.retail.promo.common;

import com.iwhalecloud.retail.dto.ResultCode;

/**
 * 返回值枚举类
 *
 * @author mzl
 * @date 2018/9/30
 */
public enum ResultCodeEnum implements ResultCode {

    SUCCESS("0","SUCCESS"),
    ERROR("-1","系统异常");

    private final String code;
    private final String desc;


    ResultCodeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode(){
        return code;
    }

    @Override
    public String getDesc(){
        return desc;
    }

}
