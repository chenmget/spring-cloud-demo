package com.iwhalecloud.retail.warehouse.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResourceReqDetailPageResp implements Serializable {

    @ApiModelProperty(value = "记录营销资源申请单标识")
    private String mktResReqId;

    @ApiModelProperty(value = "营销资源标识")
    private String mktResId;

    @ApiModelProperty(value = "产品分类")
    private String typeName;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "串码")
    private String mktResInstNbr;

    @ApiModelProperty(value = "机型名称")
    private String unitName;

    @ApiModelProperty(value = "产品型号")
    private String unitType;

    @ApiModelProperty(value = "ct码")
    private String ctCode;

    @ApiModelProperty(value = "产品编码")
    private String sn;

    //属性 begin
    /**
     * 记录营销资源申请单明细标识
     */
    @ApiModelProperty(value = "记录营销资源申请单明细标识")
    private java.lang.String mktResReqDetailId;

    @ApiModelProperty(value = "申请单号")
    private String reqCode;


    @ApiModelProperty(value = "厂商名")
    private String merchantName;

    /**
     * 记录首次创建的时间。
     */
    @ApiModelProperty(value = "记录首次创建的时间。")
    private java.util.Date createDate;

    /**
     * 记录状态变更的时间。
     */
    @ApiModelProperty(value = "记录状态变更的时间。")
    private java.util.Date statusDate;

    /**
     * 记录状态。LOVB=PUB-C-0001。
     */
    @ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
    private String statusCd;

    @ApiModelProperty(value = "记录状态中文。LOVB=PUB-C-0001。")
    private String statusCdName;


    @ApiModelProperty(value = "状态说明")
    private String remark;

    @ApiModelProperty(value = "验证描述，记录出错的原因")
    private String resultDesc;

    /**
     * 记录首次创建的时间。
     */
    @ApiModelProperty(value = "记录首次创建的时间。")
    private String createDateStr;

    /**
     * 记录状态变更的时间。
     */
    @ApiModelProperty(value = "记录状态变更的时间。")
    private String statusDateStr;

}
