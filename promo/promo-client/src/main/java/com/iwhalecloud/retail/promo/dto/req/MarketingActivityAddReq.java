package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import com.iwhalecloud.retail.promo.dto.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author mzl
 * @date 2019/1/30
 */
@Data
@ApiModel(value = "添加营销活动请求入参")
public class MarketingActivityAddReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = -6269235028920738121L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String creator;

    /**
     * 活动概述
     */
    @ApiModelProperty(value = "活动概述")
    private String brief;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String modifier;

    /**
     * 是否删除：0未删、1删除
     */
    @ApiModelProperty(value = "是否删除：0未删、1删除")
    private Integer isDeleted;

    /**
     * 营销活动code
     */
    @ApiModelProperty(value = "营销活动code")
    private String code;

    /**
     * 营销活动名称
     */
    @ApiModelProperty(value = "营销活动名称")
    private String name;

    /**
     * 营销活动描述
     */
    @ApiModelProperty(value = "营销活动描述")
    private String description;

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
     * 活动类型code
     */
    @ApiModelProperty(value = "活动类型code")
    private String activityType;

    /**
     * 活动类型名称
     */
    @ApiModelProperty(value = "活动类型名称")
    private String activityTypeName;

    /**
     * 优惠类型code
     */
    @ApiModelProperty(value = "营销活动产生的优惠的类型，可为空（无优惠的活动如预售）优惠类型：10减免20券30返利40赠送50红包")
    private String promotionTypeCode;
    /**
     * 状态：1已保存/10待审核/20审核通过/30审核不通过/40已关闭/0已取消
     */
    @ApiModelProperty(value = "状态：1已保存/10待审核/20审核通过/30审核不通过/40已关闭/0已取消")
    private String status;

    /**
     * 关联任务ID
     */
    @ApiModelProperty(value = "关联任务ID")
    private String relatedTaskId;

    /**
     * 发起方：10运营商/20供货商
     */
    @ApiModelProperty(value = "发起方：10运营商/20供货商")
    private String initiator;

    /**
     * 展示顺序
     */
    @ApiModelProperty(value = "展示顺序 ")
    private Integer sequence;

    /**
     * 活动发布url
     */
    @ApiModelProperty(value = "活动发布url")
    private String activityUrl;

    /**
     * 活动页面图片
     */
    @ApiModelProperty(value = "活动页面图片")
    private String pageImgUrl;

    /**
     * 活动顶部图片
     */
    @ApiModelProperty(value = "活动顶部图片")
    private String topImgUrl;

    /**
     * 是否推荐：0否/1是
     */
    @ApiModelProperty(value = "是否推荐：0否/1是")
    private Integer isRecommend;

    /**
     * 参与活动范围
     */
    @ApiModelProperty(value = "参与活动范围")
    private List<ActivityScopeDTO> activityScopeList;

    /**
     * 参与对象
     */
    @ApiModelProperty(value = "参与对象")
    private List<ActivityParticipantDTO> activityParticipantList;

    /**
     * 参与活动产品
     */
    @ApiModelProperty(value = "参与活动产品")
    private List<ActivityProductDTO> activityProductList;

    /**
     * 优惠信息
     */
    @ApiModelProperty(value = "优惠信息")
    private List<PromotionDTO> promotionDTOList;

    /**
     * 活动规则对象
     */
    @ApiModelProperty(value = "活动规则对象")
    private List<ActivityRuleDTO> activityRuleDTOList;


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

    @ApiModelProperty(value = "活动预付款")
    private Long advancePayAmount;

    @ApiModelProperty(value="活动参与数量")
    private Long participateNum;

    /**
     * 用户Id
     */
    @ApiModelProperty(value = "当前用户")
    private String userId;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "当前用户名称")
    private String userName;

    @ApiModelProperty(value = "岗位名称")
    private String sysPostName;

    /**
     * 关联组织 id
     */
    @ApiModelProperty(value = "关联组织 id")
    private java.lang.String orgId;
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
