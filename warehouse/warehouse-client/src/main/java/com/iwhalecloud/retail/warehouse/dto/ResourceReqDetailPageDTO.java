package com.iwhalecloud.retail.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResourceReqDetailPageDTO implements Serializable {

    @ApiModelProperty(value = "记录营销资源申请单标识")
    private String mktResReqId;

    @ApiModelProperty(value = "营销资源标识")
    private String mktResId;

    @ApiModelProperty(value = "产品类型")
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
    private String mktResReqDetailId;

    @ApiModelProperty(value = "申请单号")
    private String reqCode;


    @ApiModelProperty(value = "厂商id")
    private String merchantId;

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
    /**
     * 串码类型
     */
    @ApiModelProperty(value = "mktResInstType")
    private String mktResInstType;

    /**
     * SN码
     */
    @ApiModelProperty(value = "SN码")
    private String snCode;

    /**
     * 网络终端（包含光猫、机顶盒、融合终端）记录MAC码
     */
    @ApiModelProperty(value = "macCode")
    private String macCode;

    @ApiModelProperty(value = "记录首次创建的用户标识。")
    private java.lang.String createStaff;

    @ApiModelProperty(value = "目标营销资源仓库")
    private String destStoreId;

    @ApiModelProperty(value = "明细id")
    private String mktResReqItemId;


    /**
     * 记录本地网标识。
     */
    @ApiModelProperty(value = "记录本地网标识。")
    private java.lang.String lanId;

    /**
     * 指向公共管理区域标识
     */
    @ApiModelProperty(value = "指向公共管理区域标识")
    private java.lang.String regionId;

    /**
     * 营销资源仓库标识
     */
    @ApiModelProperty(value = "营销资源仓库标识")
    private String mktResStoreId;
}
