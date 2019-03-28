package com.iwhalecloud.retail.oms.dto.resquest;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/10/16
 */
@Data
public class RandomLogAddReq implements Serializable {

    private static final long serialVersionUID = -1416391724623407677L;
    /**
     * 验证码
     */
    private String randomCode;

    /** 业务类型
     * 1：账号注册-手机随机码校验
     * 2：账号绑定手机号码修改
     * 3：系统登录-手机验证码
     */
    private Integer busiType;

    /**
     * 信息发送类型
     * 1 短信
     * 2 邮箱账号--备用
     */
    private Integer sendType;

    /**
     * 信息接收账号
     */
    private String receviNo;
}
