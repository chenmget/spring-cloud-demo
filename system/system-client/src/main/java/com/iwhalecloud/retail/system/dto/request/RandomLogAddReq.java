package com.iwhalecloud.retail.system.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RandomLogAddReq implements Serializable{

	private static final long serialVersionUID = 1L;
	

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
     * 业务id
     */
    private String busiId;

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

    /**
     * 验证状态
     */
    private Integer validStatus;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 校验时间
     */
    private Date validTime;

    /**
     * 失效时间
     */
    private Date effDate;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 发送状态
     */
    private Integer sendStatus;
	
    /**
     * sessionId
     */
    private String sessionId;
    
}
