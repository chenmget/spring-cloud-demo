package com.iwhalecloud.retail.web.controller.b2b.member.requst;

import lombok.Data;

@Data
public class MemberRegisterRequest {

	private String mobile; // 手机号
    private String verificationCode; // 验证码
    private String name; // 昵称

}
