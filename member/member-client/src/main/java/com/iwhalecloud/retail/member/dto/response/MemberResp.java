package com.iwhalecloud.retail.member.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Member
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ES_MEMBER, 对应实体Member类")
public class MemberResp implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ES_MEMBER";
  	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "memberId")
	private java.lang.String memberId;

	/**
	 * agentId
	 */
	@ApiModelProperty(value = "agentId")
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
	private java.lang.String uname;

	/**
	 * email
	 */
	@ApiModelProperty(value = "email")
	private java.lang.String email;

	/**
	 * password
	 */
//	@ApiModelProperty(value = "password")
//	private java.lang.String password;

	/**
	 * 注册时间
	 */
	@ApiModelProperty(value = "注册时间")
	private java.util.Date addDate;

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
	@ApiModelProperty(value = "lastLoginTime")
	private java.util.Date lastLoginTime;

	/**
	 * 1.是\n            0否
	 */
	@ApiModelProperty(value = "1.是\n            0否")
	private java.lang.Long isAgent;

	/**
	 * loginCount
	 */
	@ApiModelProperty(value = "loginCount")
	private java.lang.Long loginCount;

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
	@ApiModelProperty(value = "更新时间")
	private java.util.Date updateDate;

	/**
	 * 更新人
	 */
	@ApiModelProperty(value = "更新人")
	private java.lang.String updateUser;

	/**
	 * 会员状态 1有效  0无效
	 */
	@ApiModelProperty(value = "会员状态 1有效  0无效")
	private java.lang.String status;

	/**
	 * 头像图片地址
	 */
	@ApiModelProperty(value = "头像图片地址")
	private java.lang.String headPort;


//  	//属性 begin
//	@ApiModelProperty(value = "memberId")
//  	private java.lang.String memberId;
//	@ApiModelProperty(value = "agentid")
//  	private java.lang.String agentid;
//
//	@ApiModelProperty(value = "parentid")
//  	private java.lang.String parentid;
//	@ApiModelProperty(value = "lvId")
//  	private java.lang.String lvId;
//
//	@ApiModelProperty(value = "uname")
//  	private java.lang.String uname;
//
//	@ApiModelProperty(value = "lvname")
//	private java.lang.String lvname;
//
//	@ApiModelProperty(value = "email")
//  	private java.lang.String email;
//
//	@ApiModelProperty(value = "password")
//  	private java.lang.String password;
//
//	@ApiModelProperty(value = "regtime")
//  	private java.util.Date regtime;
//
//	@ApiModelProperty(value = "pwAnswer")
//  	private java.lang.String pwAnswer;
//
//	@ApiModelProperty(value = "pwQuestion")
//	private java.lang.String pwQuestion;
//
//	@ApiModelProperty(value = "name")
//	private java.lang.String name;
//
//	@ApiModelProperty(value = "sex")
//	private java.math.BigDecimal sex;
//
//	@ApiModelProperty(value = "birthday")
//	private java.util.Date birthday;
//
//	@ApiModelProperty(value = "advance")
//	private java.lang.String advance;
//
//	@ApiModelProperty(value = "provinceId")
//	private java.math.BigDecimal provinceId;
//
//	@ApiModelProperty(value = "cityId")
//	private java.math.BigDecimal cityId;
//
//	@ApiModelProperty(value = "regionId")
//	private java.math.BigDecimal regionId;
//
//	@ApiModelProperty(value = "province")
//	private java.lang.String province;
//
//	@ApiModelProperty(value = "city")
//	private java.lang.String city;
//
//	@ApiModelProperty(value = "region")
//	private java.lang.String region;
//
//	@ApiModelProperty(value = "address")
//	private java.lang.String address;
//
//	@ApiModelProperty(value = "zip")
//	private java.lang.String zip;
//
//	@ApiModelProperty(value = "mobile")
//	private java.lang.String mobile;
//
//	@ApiModelProperty(value = "tel")
//	private java.lang.String tel;
//
//	@ApiModelProperty(value = "point")
//	private java.math.BigDecimal point;
//
//	@ApiModelProperty(value = "mp")
//	private java.math.BigDecimal mp;
//
//	@ApiModelProperty(value = "qq")
//	private java.lang.String qq;
//
//	@ApiModelProperty(value = "msn")
//	private java.lang.String msn;
//
//	@ApiModelProperty(value = "lastlogin")
//	private java.util.Date lastlogin;
//
//	@ApiModelProperty(value = "isAgent")
//	private java.math.BigDecimal isAgent;
//
//
//	@ApiModelProperty(value = "logincount")
//	private java.lang.Integer logincount;
//
//
//	@ApiModelProperty(value = "isCheked")
//	private java.lang.Integer isCheked;
//
//	@ApiModelProperty(value = "registerip")
//	private java.lang.String registerip;
//
//	@ApiModelProperty(value = "remark")
//	private java.lang.String remark;
//
//	@ApiModelProperty(value = "lvName")
//	private java.lang.String lvName;
//
//	@ApiModelProperty(value = "sourceFrom")
//	private java.lang.String sourceFrom;
//
//	@ApiModelProperty(value = "memberType")
//	private java.lang.String memberType;
//
//	@ApiModelProperty(value = "auditStatus")
//	private java.math.BigDecimal auditStatus;
//
//	@ApiModelProperty(value = "buyerUid")
//	private java.lang.String buyerUid;
//
//	@ApiModelProperty(value = "shipArea")
//	private java.lang.String shipArea;
//
//	@ApiModelProperty(value = "certCardNum")
//	private java.lang.String certCardNum;
//
//	@ApiModelProperty(value = "certAddress")
//	private java.lang.String certAddress;
//
//	@ApiModelProperty(value = "certFailureTime")
//	private java.util.Date certFailureTime;
//
//
//	@ApiModelProperty(value = "certType")
//	private java.lang.String certType;
//
//	@ApiModelProperty(value = "customertype")
//	private java.lang.String customertype;
//
//	@ApiModelProperty(value = "openId")
//	private java.lang.String openId;
//  	//属性 end
//

}
