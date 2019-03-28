package com.iwhalecloud.retail.web.controller.member.requst;

import lombok.Data;

@Data
public class MemberRegisterRequest {

	private String mobile; // 手机号
    private String verificationCode; // 验证码
    private String name; // 昵称

}
