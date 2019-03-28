package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhou.zc
 * @date 2019年02月23日
 * @Description:
 */
@Data
public class AddPreSubsidyProductReqDTO extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 8212157527523349539L;

    /**
     * 记录首次创建的员工标识。
     */
    @ApiModelProperty(value = "记录首次创建的员工标识。")
    private java.lang.String createStaff;

    /**
     * 产品基础表
     */
    @ApiModelProperty(value = "产品基础表")
    private java.lang.String productBaseId;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private java.lang.String productId;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private java.lang.Long num;

    /**
     * 优惠方式为直减时，保存减免的金额
     */
    @ApiModelProperty(value = "优惠方式为直减时，保存减免的金额")
    private java.lang.Long discountAmount;

    /**
     * 预售价格
     */
    @ApiModelProperty(value = "预售价格")
    private Long prePrice;

    /**
     * 优惠券信息
     */
    @ApiModelProperty(value = "优惠券信息")
    List<String> mktResIds;
}
