package com.iwhalecloud.retail.rights.consts;

public class RightsStatusConsts {

	/**
     * 待发放
     */
	 public static final String RIGHTS_STATUS_UNOBTAIN = "1000";
	 /**
	  * 待使用
	  */
	 public static final String RIGHTS_STATUS_UNUSED = "1200";
	 /**
	  * 已使用
	  */
	 public static final String RIGHTS_STATUS_USED = "1300";
	 /**
	  * 已作废
	  */
	 public static final String RIGHTS_STATUS_EXPIRE = "1100";
	 /**
	  * 预占
	  */
	 public static final String RIGHTS_STATUS_OCCUPY = "1400";
	 
	 /**
	  * 同一个优惠券的多个优惠券实例不能叠加使用
	  */
	 public static final String REUSE_FLAG_NO = "0";
	 /**
	  * 同一个优惠券的多个优惠券实例能叠加使用
	  */
	 public static final String REUSE_FLAG_YSE = "1";
	 /**
	  * 不同一个优惠券的多个优惠券实例不能混合使用
	  */
	 public static final String MIXUSE_FLAG_NO = "0";
	 /**
	  * 不同一个优惠券的多个优惠券实例能 混合使用
	  */
	 public static final String MIXUSE_FLAG_YSE = "1";
	 
	 /**
	  * 优惠券发放、使用对象类型  客户
	  */
	 public static final String PROVOBJ_TYPE_CUS = "1000";
	 /**
	  * 优惠券发放、使用对象类型  用户
	  */
	 public static final String PROVOBJ_TYPE_USER = "1100";
	 /**
	  * 优惠券发放、使用对象类型  账户
	  */
	 public static final String PROVOBJ_TYPE_ACC = "1200";
	 /**
	  * 优惠券发放、使用对象类型  翼支付帐号
	  */
	 public static final String PROVOBJ_TYPE_YZF = "2000";
	 /**
	  * 优惠券发放、使用对象类型  支付宝帐号
	  */
	 public static final String PROVOBJ_TYPE_ZFB = "2100";
	 /**
	  * 优惠券发放、使用对象类型  微信帐号
	  */
	 public static final String PROVOBJ_TYPE_WX = "2200";
	 
	 /**
     * 多个优惠券标识分隔符
     */
	 public static final String INST_ID_SEPARATOR = ",";
	 
	 /**
     * 默认权益实例入库数量
     */
	 public static final Integer DEFAUT_INVENTORY_NUM = 10;
	 
	 /**
	  * 优惠券领取循环周期类型：月循环
	  */
	 public static final String CYCLE_TYPE_MONTH = "M";
	 /**
	  * 周循环
	  */
	 public static final String CYCLE_TYPE_WEEK = "W";
	 /**
	  * 不循环
	  */
	 public static final String CYCLE_TYPE_NEVER = "N";

	/**
	 * 优惠券规则状态：可用
	 */
	public static final String COUPON_RULE_STATUS_USED = "1000";
	/**
	 * 优惠券类型状态：可用
	 */
	public static final String COUPON_STATUS_USED = "1000";

	/**
	 * 优惠券实例使用状态：未使用
	 */
	public static final String COUPON_INST_STATUS_NOUSED = "1000";
}
