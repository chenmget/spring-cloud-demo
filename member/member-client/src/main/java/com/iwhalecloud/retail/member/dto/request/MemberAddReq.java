package com.iwhalecloud.retail.member.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Member
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ES_MEMBER, 对应实体Member类")
public class MemberAddReq implements Serializable {
	private static final long serialVersionUID = 968836231244706451L;

	@ApiModelProperty(value = "memberId")
	private java.lang.String memberId;

	/**
	 * agentId
	 */
	@ApiModelProperty(value = "代理商ID")
	@NotEmpty(message = "代理商ID不能为空")
	private java.lang.String agentId;

	/**
	 * parentId
	 */
	@ApiModelProperty(value = "parentId")
	private java.lang.String parentId;

	/**
	 * 用户账号
	 */
	@ApiModelProperty(value = "用户账号")
	@NotEmpty(message = "用户账号不能为空")
	private java.lang.String uname;

	/**
	 * email
	 */
	@ApiModelProperty(value = "email")
	private java.lang.String email;

	/**
	 * password
	 */
	@ApiModelProperty(value = "password")
	@NotEmpty(message = "密码不能为空")
	private java.lang.String password;

	/**
	 * 注册时间
	 */
//	@ApiModelProperty(value = "注册时间")
//	private java.util.Date addDate;

	/**
	 * 昵称
	 */
	@ApiModelProperty(value = "昵称")
	private java.lang.String name;

	/**
	 * sex
	 */
	@ApiModelProperty(value = "sex")
	private java.lang.Long sex;

	/**
	 * birthday
	 */
	@ApiModelProperty(value = "birthday")
	private java.util.Date birthday;

	/**
	 * 手机号  (登录用的)
	 */
	@ApiModelProperty(value = "手机号  (登录用的)")
//	@NotEmpty(message = "手机号不能为空")
	private java.lang.String mobile;

	/**
	 * tel
	 */
	@ApiModelProperty(value = "tel")
	private java.lang.String tel;

	/**
	 * mp
	 */
	@ApiModelProperty(value = "mp")
	private java.lang.Long mp;

	/**
	 * qq
	 */
	@ApiModelProperty(value = "qq")
	private java.lang.String qq;

	/**
	 * msn
	 */
	@ApiModelProperty(value = "msn")
	private java.lang.String msn;

	/**
	 * lastLoginTime
	 */
//	@ApiModelProperty(value = "lastLoginTime")
//	private java.util.Date lastLoginTime;

	/**
	 * 1.是\n            0否
	 */
	@ApiModelProperty(value = "1.是\n            0否")
	private java.lang.Long isAgent;

	/**
	 * loginCount
	 */
//	@ApiModelProperty(value = "loginCount")
//	private java.lang.Long loginCount;

	/**
	 * regIp
	 */
	@ApiModelProperty(value = "regIp")
	private java.lang.String regIp;

	/**
	 * remark
	 */
	@ApiModelProperty(value = "remark")
	private java.lang.String remark;

	/**
	 * 1、微信，2、门店会员，3、美团会员，4、饿了么会员, 5，支付宝
	 */
	@ApiModelProperty(value = "1、微信，2、门店会员，3、美团会员，4、饿了么会员, 5，支付宝")
	private java.lang.String memberType;

	/**
	 * sourceFrom
	 */
	@ApiModelProperty(value = "sourceFrom")
	private java.lang.String sourceFrom;

	/**
	 * 注册人
	 */
	@ApiModelProperty(value = "注册人")
	private java.lang.String addUser;

	/**
	 * 更新时间
	 */
//	@ApiModelProperty(value = "更新时间")
//	private java.util.Date updateDate;

	/**
	 * 更新人
	 */
	@ApiModelProperty(value = "更新人")
	private java.lang.String updateUser;

	/**
	 * 会员状态 1有效  0无效
	 */
//	@ApiModelProperty(value = "会员状态 1有效  0无效")
//	private java.lang.String status;

	/**
	 * 头像图片地址
	 */
	@ApiModelProperty(value = "头像图片地址")
	private java.lang.String headPort;


}
