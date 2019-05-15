package com.iwhalecloud.retail.rights.dto.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhou.zc
 * @date 2019年03月01日
 * @Description: 产品活动信息
 */
@Data
public class ActivityProductResq implements Serializable {

    private static final long serialVersionUID = -890568132141146175L;

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
     * 是否删除：0未删、1删除。
     */
    @ApiModelProperty(value = "是否删除：0未删、1删除。")
    private String isDeleted;

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
    
    /**
     * 颜色
     */
    @ApiModelProperty(value = "颜色")
    private String color;
    
    /**
     * 内存
     */
    @ApiModelProperty(value = "内存")
    private String memory;
    
    /**
     * 产品类型
     */
    @ApiModelProperty(value = "产品类型")
    private String typeName;

}
