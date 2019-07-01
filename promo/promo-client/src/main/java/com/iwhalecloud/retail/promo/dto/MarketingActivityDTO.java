package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * MarketingActivity
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型act_marketing_activity, 对应实体MarketingActivity类")
public class MarketingActivityDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID
  	 */
	@ApiModelProperty(value = "ID")
  	private java.lang.String id;
	
	/**
  	 * 营销活动code
  	 */
	@ApiModelProperty(value = "营销活动Id")
  	private java.lang.String code;
	/**
  	 * 营销活动名称
  	 */
	@ApiModelProperty(value = "营销活动名称")
  	private java.lang.String name;
	
	/**
  	 * 营销活动描述
  	 */
	@ApiModelProperty(value = "营销活动描述")
  	private java.lang.String brief;
	
	/**
  	 * 营销活动描述
  	 */
	@ApiModelProperty(value = "营销活动描述")
  	private java.lang.String description;
	
	/**
  	 * 开始时间
  	 */
	@ApiModelProperty(value = "开始时间")
  	private java.util.Date startTime;
	
	/**
  	 * 结束时间
  	 */
	@ApiModelProperty(value = "结束时间")
  	private java.util.Date endTime;

	/**
	 * 活动类型
	 */
	@ApiModelProperty(value = "活动类型\n" +
			"1001-预售\n" +
			"1002-前置补贴\n" +
			"1003-返利")
  	private java.lang.String activityType;
	
	/**
  	 * 活动类型名称
  	 */
	@ApiModelProperty(value = "活动类型名称")
  	private java.lang.String activityTypeName;
	
	/**
  	 * 营销活动产生的优惠的类型，可为空（无优惠的活动如预售）
            优惠类型：10减免20券30返利40赠送50红包
  	 */
	@ApiModelProperty(value = "营销活动产生的优惠的类型，可为空（无优惠的活动如预售）优惠类型：10减免20券30返利40赠送50红包")
	private java.lang.String promotionTypeCode;
	/**
	 * 状态：
	 1    已保存
	 10  待审核
	 20  审核通过
	 40  已终止

	 30 审核不通过
	 0   已取消

	 */
	@ApiModelProperty(value = "状态：  1已保存/10待审核/20审核通过/30审核不通过/40已关闭/0已取消\n")
	private java.lang.String status;
	
	/**
  	 * 关联任务ID
  	 */
	@ApiModelProperty(value = "关联任务ID")
  	private java.lang.String relatedTaskId;
	
	/**
  	 * 发起方：10运营商/20供货商
  	 */
	@ApiModelProperty(value = "发起方：10运营商/20供货商")
  	private java.lang.String initiator;
	
	/**
  	 * 展示顺序
  	 */
	@ApiModelProperty(value = "展示顺序")
  	private java.lang.Long sequence;
	
	/**
  	 * 活动发布url
  	 */
	@ApiModelProperty(value = "活动发布url")
  	private java.lang.String activityUrl;
	
	/**
  	 * 活动页面图片
  	 */
	@ApiModelProperty(value = "活动页面图片")
  	private java.lang.String pageImgUrl;
	
	/**
  	 * 活动顶部图片
  	 */
	@ApiModelProperty(value = "活动顶部图片")
  	private java.lang.String topImgUrl;
	
	/**
  	 * 是否推荐：0否/1是
  	 */
	@ApiModelProperty(value = "是否推荐：0否/1是")
  	private java.lang.String isRecommend;
	
//	/**
//  	 * 记录首次创建的员工标识。
//  	 */
//	@ApiModelProperty(value = "记录首次创建的员工标识。")
//  	private java.lang.String createStaff;
//
//	/**
//  	 * 记录首次创建的时间。
//  	 */
//	@ApiModelProperty(value = "记录首次创建的时间。")
//  	private java.util.Date createDate;
//
//	/**
//  	 * 记录每次修改的员工标识。
//  	 */
//	@ApiModelProperty(value = "记录每次修改的员工标识。")
//  	private java.lang.String updateStaff;
//
//	/**
//  	 * 记录每次修改的时间。
//  	 */
//	@ApiModelProperty(value = "记录每次修改的时间。")
//  	private java.util.Date updateDate;
	/**
	 * 记录首次创建的员工标识。
	 */
	@ApiModelProperty(value = "创建人。")
	private java.lang.String creator;

	/**
	 * 记录首次创建的时间。
	 */
	@ApiModelProperty(value = "创建时间。")
	private java.util.Date gmtCreate;

	/**
	 * 记录每次修改的员工标识。
	 */
	@ApiModelProperty(value = "修改人。")
	private java.lang.String modifier;

	/**
	 * 记录每次修改的时间。
	 */
	@ApiModelProperty(value = "修改时间。")
	private java.util.Date gmtModified;
	/**
	 * 活动范围标识  10地区/20对象
	 */
	@ApiModelProperty(value = "活动范围标识")
	private String activityScopeType;


	/**
	 * 参与范围标识  10地区/20对象
	 */
	@ApiModelProperty(value = "参与范围标识")
	private String activityParticipantType;
	/**
	 * 支付类型
	 */
	@ApiModelProperty(value = "支付类型")
	private String payType;
	/**
	 * 支付定金开始时间
	 */
	@ApiModelProperty(value = "支付定金开始时间")
	private Date preStartTime;
	/**
	 * 支付定金结束时间
	 */
	@ApiModelProperty(value = "支付定金结束时间")
	private Date preEndTime;
	/**
	 * 支付尾款开始时间
	 */
	@ApiModelProperty(value = "支付尾款开始时间")
	private Date tailPayStartTime;
	/**
	 * 支付尾款结束时间
	 */
	@ApiModelProperty(value = "支付尾款结束时间")
	private Date tailPayEndTime;
	/**
	 * 是否删除：0未删、1删除。
	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除。")
	private String isDeleted;
	/**
	 * 用户名 userName
	 */
	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "活动预付款")
	private Long advancePayAmount;

	@ApiModelProperty(value="活动参与数量")
	private Long participateNum;
	/**
	 * 活动发货开始时间 deliver_start_time
	 */
	@ApiModelProperty(value = "活动发货开始时间 deliver_start_time")
	private Date deliverStartTime;
	/**
	 * 活动发货截止时间 deliver_end_time
	 */
	@ApiModelProperty(value = "活动发货截止时间 deliver_end_time")
	private Date deliverEndTime;

	/**
	 * 是否修改审批中：0否/1是 is_modifying
	 */
	@ApiModelProperty(value="修改标识，是否修改审批中：0否/1是 is_modifying")
	private String isModifying;

	/**
	 * 活动的优惠规则描述.promotion_desc
	 *    如前置补贴为:
	 *    省级前置补贴xx元
	 *    市级前置补贴xx元
	 *	  满减活动为:
	 *    满XX元减YY元'
	 */
	@ApiModelProperty(value="活动的优惠规则描述 promotion_desc")
	private String promotionDesc;
}
