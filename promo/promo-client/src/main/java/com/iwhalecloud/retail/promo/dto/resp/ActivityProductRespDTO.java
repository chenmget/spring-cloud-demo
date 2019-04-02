package com.iwhalecloud.retail.promo.dto.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhou.zc
 * @date 2019年03月01日
 * @Description:活动产品信息
 */
@Data
public class ActivityProductRespDTO implements Serializable {

    private static final long serialVersionUID = 5770079106901462677L;
    /**
     * ID
     */
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "ID")
    private java.lang.String id;

    /**
     * 营销活动code
     */
    @ApiModelProperty(value = "营销活动Id")
    private java.lang.String marketingActivityId;

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
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private java.lang.Long price;

    /**
     * 预付价格
     */
    @ApiModelProperty(value = "预付价格")
    private java.lang.Long prePrice;

    /**
     * 返利金额
     */
    @ApiModelProperty(value = "返利金额")
    private java.lang.Long rebatePrice;

    /**
     * 优惠方式为直减时，保存减免的金额
     */
    @ApiModelProperty(value = "优惠方式为直减时，保存减免的金额")
    private java.lang.Long discountAmount;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private java.lang.Long num;

    /**
     * 达量
     */
    @ApiModelProperty(value = "达量")
    private java.lang.Long reachAmount;


    /**
     * 供应商编码
     */
    @ApiModelProperty(value = "供应商编码")
    private java.lang.String supplierCode;

    @ApiModelProperty(value = "创建人。")
    private java.lang.String creator;

    /**
     * 记录首次创建的时间。
     */
    @ApiModelProperty(value = "创建时间。")
    private java.util.Date gmt_create;
    /**
     * 记录每次修改的员工标识。
     */
    @ApiModelProperty(value = "修改人。")
    private java.lang.String modifier;

    /**
     * 记录每次修改的时间。
     */
    @ApiModelProperty(value = "修改时间。")
    private java.util.Date gmt_modified;
    /**
     * 是否删除：0未删、1删除。
     */
    @ApiModelProperty(value = "是否删除：0未删、1删除。")
    private String isDeleted;

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
     * 记录数据来源
     */
    @ApiModelProperty(value = "记录数据来源。")
    private java.lang.String sourceFrom;

    /**
     * 是否限制产品参与总数量
     1.限制
     0.不限制
     */
    @ApiModelProperty(value = "产品参与总数量限制标识")
    private String numLimitFlg;
}
