package com.iwhalecloud.retail.system.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.KeySequence;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author he.sw
 * @date 2018/11/30
 */
@Data
@TableName("sys_random_log")
@KeySequence(value = "seq_sys_random_log_id",clazz = String.class)
public class RandomLog implements Serializable {

    private static final long serialVersionUID = -3903559699538999971L;

    /**
     * 记录id
     */
    @TableId
    private String logId;

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

}
