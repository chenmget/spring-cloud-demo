package com.iwhalecloud.retail.member.pojo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

/**
 * MemberLoginResp
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ES_MEMBER, 对应实体Member类")
public class MemberLoginResp implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ES_MEMBER";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * memberId
  	 */
	@ApiModelProperty(value = "memberId")
  	private java.lang.String memberId;
  	
  	/**
  	 * agentid
  	 */
	@ApiModelProperty(value = "agentid")
  	private java.lang.String agentid;
  	
  	/**
  	 * parentid
  	 */
	@ApiModelProperty(value = "parentid")
  	private java.lang.String parentid;
  	
  	/**
  	 * lvId
  	 */
	@ApiModelProperty(value = "lvId")
  	private java.lang.String lvId;
  	
  	/**
  	 * uname
  	 */
	@ApiModelProperty(value = "uname")
  	private java.lang.String uname;
	
	/**
	 * uname
	 */
	@ApiModelProperty(value = "lvname")
	private java.lang.String lvname;
  	
  	/**
  	 * email
  	 */
	@ApiModelProperty(value = "email")
  	private java.lang.String email;
  	
  	/**
  	 * password
  	 */
	@ApiModelProperty(value = "password")
  	private java.lang.String password;
  	
  	/**
  	 * regtime
  	 */
	@ApiModelProperty(value = "regtime")
  	private java.util.Date regtime;
  	
  	/**
  	 * pwAnswer
  	 */
	@ApiModelProperty(value = "pwAnswer")
  	private java.lang.String pwAnswer;
  	
	
	/**
	 * pwQuestion
	 */
	@ApiModelProperty(value = "pwQuestion")
	private java.lang.String pwQuestion;
	
	/**
	 * name
	 */
	@ApiModelProperty(value = "name")
	private java.lang.String name;
	
	/**
	 * sex
	 */
	@ApiModelProperty(value = "sex")
	private java.math.BigDecimal sex;
	
	/**
	 * birthday
	 */
	@ApiModelProperty(value = "birthday")
	private java.util.Date birthday;
	
	/**
	 * advance
	 */
	@ApiModelProperty(value = "advance")
	private java.lang.String advance;
	
	/**
	 * provinceId
	 */
	@ApiModelProperty(value = "provinceId")
	private java.math.BigDecimal provinceId;
	
	/**
	 * cityId
	 */
	@ApiModelProperty(value = "cityId")
	private java.math.BigDecimal cityId;
	
	/**
	 * regionId
	 */
	@ApiModelProperty(value = "regionId")
	private java.math.BigDecimal regionId;
	
	/**
	 * province
	 */
	@ApiModelProperty(value = "province")
	private java.lang.String province;
	
	/**
	 * city
	 */
	@ApiModelProperty(value = "city")
	private java.lang.String city;
	
	/**
	 * region
	 */
	@ApiModelProperty(value = "region")
	private java.lang.String region;
	
	/**
	 * address
	 */
	@ApiModelProperty(value = "address")
	private java.lang.String address;
	
	/**
	 * zip
	 */
	@ApiModelProperty(value = "zip")
	private java.lang.String zip;
	
	/**
	 * mobile
	 */
	@ApiModelProperty(value = "mobile")
	private java.lang.String mobile;
	
	/**
	 * tel
	 */
	@ApiModelProperty(value = "tel")
	private java.lang.String tel;
	
	/**
	 * point
	 */
	@ApiModelProperty(value = "point")
	private java.math.BigDecimal point;
	
	/**
	 * mp
	 */
	@ApiModelProperty(value = "mp")
	private java.math.BigDecimal mp;
	/**
	 * mp
	 */
	@ApiModelProperty(value = "qq")
	private java.lang.String qq;
	/**
	 * msn
	 */
	@ApiModelProperty(value = "msn")
	private java.lang.String msn;
	/**
	 * lastlogin
	 */
	@ApiModelProperty(value = "lastlogin")
	private java.util.Date lastlogin;
	/**
	 * isAgent
	 */
	@ApiModelProperty(value = "isAgent")
	private java.math.BigDecimal isAgent;
	/**
	 * logincount
	 */
	@ApiModelProperty(value = "logincount")
	private java.lang.Integer logincount;
	/**
	 * isCheked
	 */
	@ApiModelProperty(value = "isCheked")
	private java.lang.Integer isCheked;
	/**
	 * registerip
	 */
	@ApiModelProperty(value = "registerip")
	private java.lang.String registerip;
	/**
	 * remark
	 */
	@ApiModelProperty(value = "remark")
	private java.lang.String remark;
	
	/**
	 * lvName
	 */
	@ApiModelProperty(value = "lvName")
	private java.lang.String lvName;
	
	/**
	 * sourceFrom
	 */
	@ApiModelProperty(value = "sourceFrom")
	private java.lang.String sourceFrom;
	
	/**
	 * memberType
	 */
	@ApiModelProperty(value = "memberType")
	private java.lang.String memberType;
	
	/**
	 * auditStatus
	 */
	@ApiModelProperty(value = "auditStatus")
	private java.math.BigDecimal auditStatus;
	
	/**
	 * buyerUid
	 */
	@ApiModelProperty(value = "buyerUid")
	private java.lang.String buyerUid;
	
	/**
	 * shipArea
	 */
	@ApiModelProperty(value = "shipArea")
	private java.lang.String shipArea;
	
	/**
	 * certCardNum
	 */
	@ApiModelProperty(value = "certCardNum")
	private java.lang.String certCardNum;
	
	/**
	 * certAddress
	 */
	@ApiModelProperty(value = "certAddress")
	private java.lang.String certAddress;
	
	/**
	 * certFailureTime
	 */
	@ApiModelProperty(value = "certFailureTime")
	private java.util.Date certFailureTime;
	
	/**
	 * certType
	 */
	@ApiModelProperty(value = "certType")
	private java.lang.String certType;
	
	/**
	 * customertype
	 */
	@ApiModelProperty(value = "customertype")
	private java.lang.String customertype;
	
	/**
	 * openId
	 */
	@ApiModelProperty(value = "openId")
	private java.lang.String openId;
  	//属性 end
	

}
