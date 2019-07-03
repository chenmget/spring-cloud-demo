package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2019/1/30
 */
@Data
public class MarketingActivityListReq extends AbstractPageReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 营销活动名称
     */
    @ApiModelProperty(value = "营销活动名称")
    private String activityName;

    /**
     * 营销活动编码
     */
    @ApiModelProperty(value = "营销活动编码")
    private String activityCode;

    /**
     * 营销活动类型
     */
    @ApiModelProperty(value = "营销活动类型")
    private String activityType;

    /**
     * 活动级别，记录活动发起的对象级别 activity_level
     *  1. 省级活动（运营商省级管理员发起）
     *  2. 地市级活动（运营商地制级管理员发起）
     *  3. 厂商活动（厂商自行发起）
     *  4. 国省包商活动（国省包供应商自行发起）
     *  5. 地包商活动（地包供应商自行发起）',
     */
    @ApiModelProperty(value="活动级别，记录活动发起的对象级别 activity_level")
    private String activityLevel;

    /**
     * 营销活动发起人
     */
    @ApiModelProperty(value = "营销活动发起人")
    private String activityInitiator;

    /**
     * 营销活动状态
     */
    @ApiModelProperty(value = "营销活动状态")
    private String activityStatus;

    /**
     * 营销活动开始时间起
     */
    @ApiModelProperty(value = "营销活动开始时间起")
    private String startTimeS;

    /**
     * 营销活动开始时间止
     */
    @ApiModelProperty(value = "营销活动开始时间止")
    private String startTimeE;

    /**
     * 营销活动结束时间起
     */
    @ApiModelProperty(value = "营销活动结束时间起")
    private String endTimeS;

    /**
     * 营销活动结束时间止
     */
    @ApiModelProperty(value = "营销活动结束时间止")
    private String endTimeE;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String creator;

    /**
     * 创建人id列表
     */
    @ApiModelProperty(value = "创建人id列表")
    private String creatorIdList;
    
    /**
     * 活动发货开始时间起
     */
    @ApiModelProperty(value = "活动发货开始时间起")
    private String deliverStartTimeS;
    
    /**
     * 活动发货开始时间止
     */
    @ApiModelProperty(value = "活动发货开始时间止")
    private String deliverStartE;
    
    /**
     * 活动发货截止时间起
     */
    @ApiModelProperty(value = "活动发货截止时间起")
    private String deliverEndTimeS;	
    
    /**
     * 活动发货截止时间止
     */
    @ApiModelProperty(value = "活动发货截止时间止")
    private String deliverEndTimeE;

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
