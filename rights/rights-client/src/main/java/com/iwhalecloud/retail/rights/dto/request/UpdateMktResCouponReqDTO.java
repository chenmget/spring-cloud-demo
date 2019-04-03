package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhou.zc
 * @date 2019年02月22日
 * @Description:
 */
@Data
public class UpdateMktResCouponReqDTO extends AbstractRequest implements Serializable{

    private static final long serialVersionUID = 6919352468559442421L;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateStaff;

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
     * 合作伙伴标识
     */
    @ApiModelProperty(value = "合作伙伴标识")
    private Long partnerId;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateDate;

    @ApiModelProperty(value = "券种类")
    private String couponKind;
}
