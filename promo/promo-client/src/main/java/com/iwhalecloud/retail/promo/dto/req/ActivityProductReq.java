package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 添加参与活动产品请求体
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年02月19日
 */
@Data
@ApiModel(value = "添加活动产品请求入参")
public class ActivityProductReq implements Serializable{

    private static final long serialVersionUID = -2315967816126822734L;

    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("创建人")
    private String creator;

    @ApiModelProperty("修改人")
    private String modifier;

    @ApiModelProperty("是否删除")
    private Integer isDelete;

    @ApiModelProperty("营销活动主键")
    private String marketingActivityId;

    @ApiModelProperty("产品基础表")
    private String productBaseId;

    @ApiModelProperty("产品Id")
    private String productId;

    @ApiModelProperty("价格")
    private Long price;

    @ApiModelProperty("减免金额")
    private Long discountAmount;

    @ApiModelProperty("预付价格")
    private Long prePrice;

    @ApiModelProperty("返利金额")
    private Long rebatePrice;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("达量")
    private Long reachAmount;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("数据来源")
    private String sourceFrom;
}
