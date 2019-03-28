package com.iwhalecloud.retail.oms.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("t_member_binding")
@Data
public class MemberBinding implements Serializable {

    private static final long serialVersionUID = 6708509690491788027L;

    @TableId(type = IdType.ID_WORKER)
    private Long id; // 不能用int类型  new对象时会初始化为0  影响查询语句

    private String memberId; // 会员ID
    private String uname; // 会员账号
    private String targetType; // 第三方平台类型: 1:微信; 2:支付宝; 3: QQ  ...
    private String targetId; // 第三方账号特定ID（如微信的openId）

}
