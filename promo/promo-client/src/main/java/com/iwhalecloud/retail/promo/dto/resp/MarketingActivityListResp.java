package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mzl
 * @date 2019/1/30
 */
@Data
public class MarketingActivityListResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 营销活动Id
     */
    @ApiModelProperty(value = "营销活动Id")
    private String id;
    
    /**
     * 营销活动名称
     */
    @ApiModelProperty(value = "营销活动名称")
    private String name;

    /**
     * 营销活动名称
     */
    @ApiModelProperty(value = "营销活动编码")
    private String code;

    /**
     * 营销活动类型
     */
    @ApiModelProperty(value = "营销活动类型")
    private String activityType;

    /**
     * 发起方：10运营商/20供货商
     */
    @ApiModelProperty(value = "发起方：10运营商/20供货商")
    private String initiator;

    /**
     * 定金
     */
    @ApiModelProperty(value = "定金")
    private String prePrice;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "创建人")
    private String creatorName;
    /**
     * 开始时间
     */

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    /**
     * 结束时间
     */

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    /**
     * 状态：1已保存/10待审核/20审核通过/30审核不通过/40已关闭/0已取消
     */
    @ApiModelProperty(value = "状态：1已保存/10待审核/20审核通过/30审核不通过/40已关闭/0已取消")
    private Integer status;
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
}
