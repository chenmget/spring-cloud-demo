package com.iwhalecloud.retail.warehouse.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResourceRequestQueryResp implements Serializable {
    /**
     * 查询表PROD_TYPE使用
     */
    @ApiModelProperty(value = "产品分类")
    private String typeId;
    /**
     * 查询表PROD_TYPE使用
     */
    @ApiModelProperty(value = "产品分类")
    private String typeName;

    /**
     * 申请单号
     */
    @ApiModelProperty(value = "申请单编码")
    private String reqCode;

    /**
     * 申请单类型
     */
    @ApiModelProperty(value = "申请单类型")
    private String reqType;

    /**
     * 记录营销资源申请单标识
     */
    @ApiModelProperty(value = "记录营销资源申请单标识")
    private String mktResReqId;

    /**
     * 营销资源标识，记录product_id
     */
    @ApiModelProperty(value = "营销资源标识，记录product_id")
    private java.lang.String mktResId;

    /**
     * 记录营销资源实例的数量
     */
    @ApiModelProperty(value = "记录营销资源实例的数量")
    private java.lang.Long quantity;

    /**
     * 目标营销资源仓库标识
     */
    @ApiModelProperty(value = "目标营销资源仓库标识")
    private String mktResStoreId;

    /**
     * 目标营销资源仓库标识
     */
    @ApiModelProperty(value = "目标营销资源仓库标识")
    private String mktResStoreName;

    /**
     * 目标营销资源仓库
     */
    @ApiModelProperty(value = "目标营销资源仓库")
    private String destStoreId;

    /**
     * 目标营销资源仓库
     */
    @ApiModelProperty(value = "目标营销资源仓库")
    private String destStoreName;

    /**
     * 记录状态。LOVB=RES-C-0010
     */
    @ApiModelProperty(value = "调货状态。LOVB=RES-C-0010")
    private String statusCd;


    /**
     * 记录首次创建的时间。
     */
    @ApiModelProperty(value = "记录首次创建的时间。")
    private java.util.Date createDate;


}
