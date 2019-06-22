package com.iwhalecloud.retail.oms.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class MemberBindingDTO implements Serializable {

    private static final long serialVersionUID = 6708509690491788027L;

    private Long id; // 不能用int类型  new对象时会初始化为0  影响查询语句

    private String memberId; // 会员ID
    private String uname; // 会员账号
    private String targetType; // 第三方平台类型: 1:微信; 2:支付宝; 3: QQ  ...
    private String targetId; // 第三方账号特定ID（如微信的openId）

}
