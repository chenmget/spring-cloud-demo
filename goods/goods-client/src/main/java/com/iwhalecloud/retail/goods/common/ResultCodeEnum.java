package com.iwhalecloud.retail.goods.common;

/**
 * 返回值枚举类
 *
 * @author mzl
 * @date 2018/9/30
 */
public enum ResultCodeEnum {

    SUCCESS("0","SUCCESS"),
    ERROR("-1","ERROR"),
    ROP_INVOKE_EXCEPTION("-2","ROP INVOKE EXCEPTION"),
    LACK_OF_PARAM("-3","LACK_OF_PARAM"),
    INSERT_DB_EXCEPTION("-5","INSERT_DB_EXCEPTION"),
    SPEC_VALUE_NOT_EXIST("-6","规格值不存在"),
    LENGTH_UNEQUAL("-7","规格价格和规格库存的数组长度必须和规格的数组长度一致"),
    SERVICE_INVOKE_EXCEPTION("-8","服务调用异常"),
    NOT_LOGIN("100","用户未登录"),
    SYSTEM_ERROR("500","服务器内部错误"),
    PAGE_NOT_FOUND("404","页面找不到");

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
