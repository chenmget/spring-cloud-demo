package com.iwhalecloud.retail.system.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class VerifyCodeGetReq extends BaseZopMsgReq implements Serializable {

    /**
     * 1：账号注册-手机随机码校验n            2：账号绑定手机号码修改n            3：系统登录-手机验证码',
     */
    private int operatType;
}
