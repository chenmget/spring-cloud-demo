package com.iwhalecloud.retail.oms.dto.resquest;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberRegisterRequestDTO implements Serializable {

    private String mobile; // 手机号
    private String verificationCode; // 验证码
//    private String loginType; // 登陆类型： 1：密码登陆   2：验证码登陆
    private String name; // 昵称

}