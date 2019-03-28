package com.iwhalecloud.retail.oms.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 小程序通过code获取用户的appid信息
 * @author Z
 *
 */
@Data
public class CodeSessionDTO implements Serializable {

	private static final long serialVersionUID = -8391036400354459683L;

	/**
	 * 用户唯一标识	
	 */
	private String openid;
	/**
	 * 会话密钥
	 */
	private String session_key;
	
	/**
	 * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
	 */
	private String unionid;
	/**
	 * 错误码
	 * -1	系统繁忙，此时请开发者稍候再试
	 * 0	请求成功
	 * 40029	code 无效
	 * 45011	频率限制，每个用户每分钟100次	
	 */
	private String errcode;
	
	/**
	 * 错误信息
	 */
	private String errMsg;	
}
