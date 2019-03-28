package com.iwhalecloud.retail.order2b.consts.order;

import org.springframework.util.StringUtils;

/**
 * 登录类型(loginUserType)
 */
public enum LoginUserType {

    NULL("","",""),
    LOGIN_USER_TYPE_M("会员","memberId",""),
    LOGIN_USER_TYPE_O("运营人员","operatorId","(分销商)"),
    LOGIN_USER_TYPE_S("供应商","supperId",""),
    LOGIN_USER_TYPE_SM("超级管理员","manager",""),

    ;
    public static LoginUserType matchOpCode(String opCodeStr) {
        if(StringUtils.isEmpty(opCodeStr)){
            return LoginUserType.NULL;
        }
        for (LoginUserType opCode : LoginUserType.values()) {
            if (opCode.getCode().equalsIgnoreCase(opCodeStr)) {
                return opCode;
            }
        }
        return LoginUserType.NULL;
    }

    LoginUserType(String name, String code, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    private String code;
    private String name;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
