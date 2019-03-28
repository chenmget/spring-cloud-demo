package com.iwhalecloud.retail.oms.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author mzl
 * @date 2018/10/16
 */
@Data
@TableName("t_random_log")
public class TRandomLog implements Serializable {

    private static final long serialVersionUID = -3903559699538999971L;

    /**
     * 记录id
     */
    @TableId(type = IdType.ID_WORKER)
    private Long logId;

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
