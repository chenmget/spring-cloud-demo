package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zhou.zc
 * @date 2019年02月22日
 * @Description:
 */
@Data
public class UpdatePreSubsidyCouponReqDTO extends AbstractRequest implements Serializable{

    private static final long serialVersionUID = -4996900918397181838L;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateStaff;

    /**
     * 营销活动Id
     */
    @ApiModelProperty(value = "营销活动Id")
    private java.lang.String marketingActivityId;

    /**
     * 营销资源标识,唯一主键
     */
    @ApiModelProperty(value = "营销资源标识,唯一主键")
    private String mktResId;

    /**
     * 营销资源名称
     */
    @ApiModelProperty(value = "营销资源名称")
    private String mktResName;

    /**
     * 展示额度
     */
    @ApiModelProperty(value = "展示额度")
    private Long showAmount;

    /**
     * 券类型
     */
    @ApiModelProperty(value = "券类型")
    private String couponType;

    /**
     * 成本类型
     */
    @ApiModelProperty(value = "成本类型")
    private java.lang.String manageType;

    /**
     * 优惠方式
     */
    @ApiModelProperty(value = "优惠方式")
    private String discountType;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 优惠券生失效规则标识，唯一主键
     */
    @NotNull
    @ApiModelProperty(value = "优惠券生失效规则标识，唯一主键")
    private Long effExpRuleId;

    /**
     * 生效时间
     */
    @ApiModelProperty(value = "生效时间")
    private java.util.Date effDate;

    /**
     * 失效时间
     */
    @ApiModelProperty(value = "失效时间")
    private java.util.Date expDate;

    /**
     * 优惠券领取规则标识，主键
     */
    @NotNull
    @ApiModelProperty(value = "优惠券领取规则标识，主键")
    private Long supplyRuleId;

    /**
     * 领取周期类型
     */
    @ApiModelProperty(value = "领取周期类型")
    private String cycleType;

    /**
     * 领取数量
     */
    @ApiModelProperty(value = "领取数量")
    private Long supplyNum;

    /**
     * 领取总量
     */
    @ApiModelProperty(value = "领取总量")
    private Long maxNum;

    /**
     * 领取规则描述
     */
    @ApiModelProperty(value = "领取规则描述")
    private String supplyRuleDesc;

    /**
     * 抵扣规则标识
     */
    @NotNull
    @ApiModelProperty(value = "抵扣规则标识")
    private Long discountRuleId;

    /**
     * 抵扣额
     */
    @ApiModelProperty(value = "抵扣额")
    private Double discountValue;

    /**
     * 抵扣上限
     */
    @ApiModelProperty(value = "抵扣上限")
    private Double maxValue;

    /**
     * 抵扣下限
     */
    @ApiModelProperty(value = "抵扣下限")
    private Double minValue;

    /**
     * 抵扣条件
     */
    @ApiModelProperty(value = "抵扣条件")
    private Double ruleAmount;

    /**
     * 是否叠加使用
     */
    @ApiModelProperty(value = "是否叠加使用")
    private String reuseFlag;

    /**
     * 是否循环使用
     */
    @ApiModelProperty(value = "是否循环使用")
    private String recycleFlag;

    /**
     * 是否混合使用
     */
    @ApiModelProperty(value = "是否混合使用")
    private String mixUseFlag;

    /**
     * 合作伙伴标识
     */
    @ApiModelProperty(value = "合作伙伴标识")
    private Long partnerId;
}
