package com.iwhalecloud.retail.warehouse.dto.response.markresswap;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/6 20:51
 */
@Data
@ApiModel("查询零售商仓库终端的实例列表按多种条件返回参数")
public class QryMktInstInfoByConditionItemSwapResp implements Serializable {

    @ApiModelProperty(value = "实例标识")
    private String mktResInstId;
    @ApiModelProperty(value = "串码")
    private String barCode;
    @ApiModelProperty(value = "资源标识")
    private String mktResId;
    @ApiModelProperty(value = "营销资源名称")
    private String mktResName;
    @ApiModelProperty(value = "仓库标识")
    private String storeId;
    @ApiModelProperty(value = "仓库名称")
    private String storeName;
    @ApiModelProperty(value = "串码类型")
    private String currState;
    @ApiModelProperty(value = "零售价格")
    private String price;
    @ApiModelProperty(value = "本地网标识")
    private String lanId;
    @ApiModelProperty(value = "本地网名称")
    private String lanName;
    @ApiModelProperty(value = "区域标识")
    private String regionId;
    @ApiModelProperty(value = "区域名称")
    private String regionName;
    @ApiModelProperty(value = "状态时间")
    private String statusDate;
    @ApiModelProperty(value = "状态")
    private String state;
    @ApiModelProperty(value = "入库时间")
    private String createDate;
    @ApiModelProperty(value = "商家标识")
    private String providerCode;
    @ApiModelProperty(value = "商家名称")
    private String providerName;
    @ApiModelProperty(value = "地市级供货商ID")
    private String citySupplyId;
    @ApiModelProperty(value = "地市级供货商名称")
    private String citySupplyName;
    @ApiModelProperty(value = "省级供货商ID")
    private String provSupplyId;
    @ApiModelProperty(value = "省级供货商名称")
    private String provSupplyName;
    @ApiModelProperty(value = "采购类型")
    private String purchaseType;

    @ApiModelProperty(value = "营销资源编码")
    private String mktResNbr;
}

