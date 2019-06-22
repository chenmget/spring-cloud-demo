package com.iwhalecloud.retail.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("SYS_USER_LOGIN_LOG")
@ApiModel(value = "SYS_USER_LOGIN_LOG, 对应实体LoginLog类")
public class LoginLog implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TNAME = "SYS_USER_LOGIN_LOG";

	/**
  	 * 登录日志ID
  	 */
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "logId")
    private String logId;

	/**
  	 * 用户ID
  	 */
    @ApiModelProperty(value = "userId")
    private String userId;

	/**
  	 * 登录时间
  	 */
    @ApiModelProperty(value = "loginTime")
    private String loginTime;

	/**
  	 * 登出时间
  	 */
    @ApiModelProperty(value = "logoutTime")
    private String logoutTime;

	/**
  	 * 登录方式 1. 公网地址 2. 统一门户 3. 内网地址 4. APP
  	 */
    @ApiModelProperty(value = "loginType")
    private String loginType;

	/**
  	 * 记录用户登录时的请求来源IP地址
  	 */
    @ApiModelProperty(value = "sourceIp")
    private String sourceIp;

	/**
  	 * 登陆认证结果 0：失败 1：成功
  	 */
    @ApiModelProperty(value = "loginStatus")
    private String loginStatus;

	/**
  	 * 记录用户登录失败时的原因描述
  	 */
    @ApiModelProperty(value = "loginDesc")
    private String loginDesc;

}