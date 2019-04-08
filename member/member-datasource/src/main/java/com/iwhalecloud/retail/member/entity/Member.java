package com.iwhalecloud.retail.member.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("mem_member")
@KeySequence(value = "seq_mem_member_id")
@ApiModel(value = "对应模型mem_member, 对应实体Member类")
public class Member implements Serializable {
	/**表名常量*/
	public static final String TNAME = "mem_member";
	private static final long serialVersionUID = 1L;

	//属性 begin
	/**
	 * memberId
	 */
	@TableId(type = IdType.INPUT)
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
	@ApiModelProperty(value = "password")
	private java.lang.String password;

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


	//属性 end

	/** 字段名称枚举. */
	public enum FieldNames {
		/** memberId. */
		memberId("memberId","MEMBER_ID"),

		/** agentId. */
		agentId("agentId","AGENT_ID"),

		/** parentId. */
		parentId("parentId","PARENT_ID"),

		/** 用户账号. */
		uname("uname","UNAME"),

		/** email. */
		email("email","EMAIL"),

		/** password. */
		password("password","PASSWORD"),

		/** 注册时间. */
		addDate("addDate","ADD_DATE"),

		/** 昵称. */
		name("name","NAME"),

		/** sex. */
		sex("sex","SEX"),

		/** birthday. */
		birthday("birthday","BIRTHDAY"),

		/** 手机号  (登录用的). */
		mobile("mobile","MOBILE"),

		/** tel. */
		tel("tel","TEL"),

		/** mp. */
		mp("mp","MP"),

		/** qq. */
		qq("qq","QQ"),

		/** msn. */
		msn("msn","MSN"),

		/** lastLoginTime. */
		lastLoginTime("lastLoginTime","LAST_LOGIN_TIME"),

		/** 1.是\n            0否. */
		isAgent("isAgent","IS_AGENT"),

		/** loginCount. */
		loginCount("loginCount","LOGIN_COUNT"),

		/** regIp. */
		regIp("regIp","REG_IP"),

		/** remark. */
		remark("remark","REMARK"),

		/** 1、微信，2、门店会员，3、美团会员，4、饿了么会员, 5，支付宝. */
		memberType("memberType","MEMBER_TYPE"),

		/** sourceFrom. */
		sourceFrom("sourceFrom","SOURCE_FROM"),

		/** 注册人. */
		addUser("addUser","ADD_USER"),

		/** 更新时间. */
		updateDate("updateDate","UPDATE_DATE"),

		/** 更新人. */
		updateUser("updateUser","UPDATE_USER"),

		/** 会员状态 1有效  0无效. */
		status("status","STATUS"),

		/** 头像图片地址. */
		headPort("headPort","HEAD_PORT");

		private String fieldName;
		private String tableFieldName;
		FieldNames(String fieldName, String tableFieldName){
			this.fieldName = fieldName;
			this.tableFieldName = tableFieldName;
		}

		public String getFieldName() {
			return fieldName;
		}

		public String getTableFieldName() {
			return tableFieldName;
		}
	}

//	//属性 begin
//	/**
//	 * memberId
//	 */
//	@TableId(type = IdType.ID_WORKER_STR)
//	@ApiModelProperty(value = "memberId")
//	private java.lang.String memberId;
//
//	/**
//	 * 手机
//	 */
//	@ApiModelProperty(value = "mobile 手机号（登录用）")
//	private java.lang.String mobile;
//
//	/**
//	 * 账号
//	 */
//	@ApiModelProperty(value = "uname 账号")
//	private java.lang.String uname;
//
//	/**
//	 * agentId
//	 */
//	@ApiModelProperty(value = "agentId")
//	private java.lang.String agentId;
//
//	/**
//	 * parentId
//	 */
//	@ApiModelProperty(value = "parentId")
//	private java.lang.String parentId;
//
//	/**
//	 * lvId
//	 */
//	@ApiModelProperty(value = "lvId")
//	private java.lang.String lvId;
//
//	/**
//	 * email
//	 */
//	@ApiModelProperty(value = "email")
//	private java.lang.String email;
//
//	/**
//	 * password
//	 */
//	@ApiModelProperty(value = "password")
//	private java.lang.String password;
//
//	/**
//	 * regTime
//	 */
//	@ApiModelProperty(value = "regTime")
//	private java.util.Date regTime;
//
//	/**
//	 * name
//	 */
//	@ApiModelProperty(value = "name")
//	private java.lang.String name;
//
//	/**
//	 * sex
//	 */
//	@ApiModelProperty(value = "sex")
//	private java.lang.Long sex;
//
//	/**
//	 * birthday
//	 */
//	@ApiModelProperty(value = "birthday")
//	private java.util.Date birthday;
//
//	/**
//	 * provinceId
//	 */
//	@ApiModelProperty(value = "provinceId")
//	private java.lang.Long provinceId;
//
//	/**
//	 * cityId
//	 */
//	@ApiModelProperty(value = "cityId")
//	private java.lang.Long cityId;
//
//	/**
//	 * regionId
//	 */
//	@ApiModelProperty(value = "regionId")
//	private java.lang.Long regionId;
//
//	/**
//	 * province
//	 */
//	@ApiModelProperty(value = "province")
//	private java.lang.String province;
//
//	/**
//	 * city
//	 */
//	@ApiModelProperty(value = "city")
//	private java.lang.String city;
//
//	/**
//	 * region
//	 */
//	@ApiModelProperty(value = "region")
//	private java.lang.String region;
//
//	/**
//	 * address
//	 */
//	@ApiModelProperty(value = "address")
//	private java.lang.String address;
//
//	/**
//	 * zip
//	 */
//	@ApiModelProperty(value = "zip")
//	private java.lang.String zip;
//
//
//	/**
//	 * tel
//	 */
//	@ApiModelProperty(value = "tel")
//	private java.lang.String tel;
//
//	/**
//	 * point
//	 */
//	@ApiModelProperty(value = "point")
//	private java.lang.Long point;
//
//	/**
//	 * mp
//	 */
//	@ApiModelProperty(value = "mp")
//	private java.lang.Long mp;
//
//	/**
//	 * qq
//	 */
//	@ApiModelProperty(value = "qq")
//	private java.lang.String qq;
//
//	/**
//	 * msn
//	 */
//	@ApiModelProperty(value = "msn")
//	private java.lang.String msn;
//
//	/**
//	 * lastLoginTime
//	 */
//	@ApiModelProperty(value = "lastLoginTime")
//	private java.util.Date lastLoginTime;
//
//	/**
//	 * 1.是\n            0否
//	 */
//	@ApiModelProperty(value = "1.是\n            0否")
//	private java.lang.Long isAgent;
//
//	/**
//	 * loginCount
//	 */
//	@ApiModelProperty(value = "loginCount")
//	private java.lang.Long loginCount;
//
//	/**
//	 * isCheked
//	 */
//	@ApiModelProperty(value = "isCheked")
//	private java.lang.Boolean isCheked;
//
//	/**
//	 * regIp
//	 */
//	@ApiModelProperty(value = "regIp")
//	private java.lang.String regIp;
//
//	/**
//	 * remark
//	 */
//	@ApiModelProperty(value = "remark")
//	private java.lang.String remark;
//
//	/**
//	 * lvName
//	 */
//	@ApiModelProperty(value = "lvName")
//	private java.lang.String lvName;
//
//	/**
//	 * 会员类型
//	 */
//	@ApiModelProperty(value = "会员类型")
//	private java.lang.String memberType;
//
//	/**
//	 * auditStatus
//	 */
//	@ApiModelProperty(value = "auditStatus")
//	private java.math.BigDecimal auditStatus;
//
//	/**
//	 * shipArea
//	 */
//	@ApiModelProperty(value = "shipArea")
//	private java.lang.String shipArea;
//
//	/**
//	 * certCardNum
//	 */
//	@ApiModelProperty(value = "certCardNum")
//	private java.lang.String certCardNum;
//
//	/**
//	 * certAddress
//	 */
//	@ApiModelProperty(value = "certAddress")
//	private java.lang.String certAddress;
//
//	/**
//	 * certFailureTime
//	 */
//	@ApiModelProperty(value = "certFailureTime")
//	private java.util.Date certFailureTime;
//
//	/**
//	 * certType
//	 */
//	@ApiModelProperty(value = "certType")
//	private java.lang.String certType;
//
//	/**
//	 * customerType
//	 */
//	@ApiModelProperty(value = "customerType")
//	private java.lang.String customerType;
//
//
//	//属性 end
//
//	/** 字段名称枚举. */
//	public enum FieldNames {
//		/** memberId. */
//		memberId("memberId","MEMBER_ID"),
//
//		/** agentId. */
//		agentId("agentId","AGENT_ID"),
//
//		/** parentId. */
//		parentId("parentId","PARENT_ID"),
//
//		/** lvId. */
//		lvId("lvId","LV_ID"),
//
//		/** uname. */
//		uname("uname","UNAME"),
//
//		/** email. */
//		email("email","EMAIL"),
//
//		/** password. */
//		password("password","PASSWORD"),
//
//		/** regTime. */
//		regTime("regTime","REG_TIME"),
//
//		/** name. */
//		name("name","NAME"),
//
//		/** sex. */
//		sex("sex","SEX"),
//
//		/** birthday. */
//		birthday("birthday","BIRTHDAY"),
//
//		/** provinceId. */
//		provinceId("provinceId","PROVINCE_ID"),
//
//		/** cityId. */
//		cityId("cityId","CITY_ID"),
//
//		/** regionId. */
//		regionId("regionId","REGION_ID"),
//
//		/** province. */
//		province("province","PROVINCE"),
//
//		/** city. */
//		city("city","CITY"),
//
//		/** region. */
//		region("region","REGION"),
//
//		/** address. */
//		address("address","ADDRESS"),
//
//		/** zip. */
//		zip("zip","ZIP"),
//
//		/** mobile. */
//		mobile("mobile","MOBILE"),
//
//		/** tel. */
//		tel("tel","TEL"),
//
//		/** point. */
//		point("point","POINT"),
//
//		/** mp. */
//		mp("mp","MP"),
//
//		/** qq. */
//		qq("qq","QQ"),
//
//		/** msn. */
//		msn("msn","MSN"),
//
//		/** lastLoginTime. */
//		lastLoginTime("lastLoginTime","LAST_LOGIN_TIME"),
//
//		/** 1.是\n            0否. */
//		isAgent("isAgent","IS_AGENT"),
//
//		/** loginCount. */
//		loginCount("loginCount","LOGIN_COUNT"),
//
//		/** isCheked. */
//		isCheked("isCheked","IS_CHEKED"),
//
//		/** regIp. */
//		regIp("regIp","REG_IP"),
//
//		/** remark. */
//		remark("remark","REMARK"),
//
//		/** lvName. */
//		lvName("lvName","LV_NAME"),
//
//		/** 会员类型. */
//		memberType("memberType","MEMBER_TYPE"),
//
//		/** auditStatus. */
//		auditStatus("auditStatus","AUDIT_STATUS"),
//
//		/** shipArea. */
//		shipArea("shipArea","SHIP_AREA"),
//
//		/** certCardNum. */
//		certCardNum("certCardNum","CERT_CARD_NUM"),
//
//		/** certAddress. */
//		certAddress("certAddress","CERT_ADDRESS"),
//
//		/** certFailureTime. */
//		certFailureTime("certFailureTime","CERT_FAILURE_TIME"),
//
//		/** certType. */
//		certType("certType","CERT_TYPE"),
//
//		/** customerType. */
//		customerType("customerType","CUSTOMER_TYPE");
//
//		private String fieldName;
//		private String tableFieldName;
//		FieldNames(String fieldName, String tableFieldName){
//			this.fieldName = fieldName;
//			this.tableFieldName = tableFieldName;
//		}
//
//		public String getFieldName() {
//			return fieldName;
//		}
//
//		public String getTableFieldName() {
//			return tableFieldName;
//		}
//	}

}
