package com.iwhalecloud.retail.web.controller.b2b.fdfs.consts;

import com.iwhalecloud.retail.dto.ResultCode;

/**
 * 返回值枚举类
 *
 * @author mzl
 * @date 2018/9/30
 */
public enum FileResultCodeEnum implements ResultCode {

    SUCCESS("0","SUCCESS"),
    ERROR("-1","ERROR"),
    ROP_INVOKE_EXCEPTION("-2","ROP INVOKE EXCEPTION"),
    LACK_OF_PARAM("-3","LACK_OF_PARAM"),
    INSERT_DB_EXCEPTION("-5","INSERT_DB_EXCEPTION"),
    UPDATE_DB_EXCEPTION("-6","UPDATE_DB_EXCEPTION"),
    SPEC_VALUES_CAN_NOT_BE_NULL("-7","规格值不能为空"),
    FORBID_CONSUMER("-8","缺少服务提供"),
    FILE_UPLOAD_ERROR("-9","附件上传失败"),
    FORBID_UPLOAD_ERROR("-10","非法的附件类型"),
    NOT_LOGIN("100","用户未登录"),
    INVALID_TOKEN("101","TOKEN无效"),
    LOST_TOKEN("102","TOKEN缺失"),
    NOT_BINDING_WX("103","用户未绑定"),//未绑定微信
    SYSTEM_ERROR("500","服务器内部错误"),
    PAGE_NOT_FOUND("404","页面找不到"),
    PARAMTER_ERROR("400","请求参数异常");

    private final String code;
    private final String desc;


    FileResultCodeEnum(String code, String desc){
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
