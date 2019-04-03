package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lhr 2019-04-03 10:14:30
 */
@Data
public class ReBateActivityListReq extends AbstractPageReq implements Serializable{
    private static final long serialVersionUID = 7580659393482076684L;
    /**
     * 返利活动名称
     */
    @ApiModelProperty(value = "返利活动名称")
    private String activityName;

    /**
     * 返利计算规则
     */
    @ApiModelProperty(value = "返利计算规则")
    private String activityRule;


    /**
     * 活动发起人
     */
    @ApiModelProperty(value = "活动发起人")
    private String activityInitiator;

    /**
     * 活动状态
     */
    @ApiModelProperty(value = "活动状态")
    private String activityStatus;

    /**
     * 活动开始时间起
     */
    @ApiModelProperty(value = "活动开始时间起")
    private String startTimeS;

    /**
     * 活动开始时间止
     */
    @ApiModelProperty(value = "活动开始时间止")
    private String startTimeE;

    /**
     * 活动结束时间起
     */
    @ApiModelProperty(value = "活动结束时间起")
    private String endTimeS;

    /**
     * 活动结束时间止
     */
    @ApiModelProperty(value = "活动结束时间止")
    private String endTimeE;

}
