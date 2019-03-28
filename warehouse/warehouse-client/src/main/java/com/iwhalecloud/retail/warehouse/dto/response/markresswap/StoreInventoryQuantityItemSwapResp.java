package com.iwhalecloud.retail.warehouse.dto.response.markresswap;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/6 21:03
 */
@Data
@ApiModel("查询零售商仓库终端的库存数量按条件返回参数")
public class StoreInventoryQuantityItemSwapResp  implements Serializable {



    @ApiModelProperty(value = "资源标识")
    private String mktResId;
    @ApiModelProperty(value = "营销资源名称")
    private String mktResName;
    @ApiModelProperty(value = "仓库标识")
    private String storeId;
    @ApiModelProperty(value = "仓库名称")
    private String storeName;
    @ApiModelProperty(value = "库存数量")
    private String storeNum;
    @ApiModelProperty(value = "商家编码")
    private String provider;
    @ApiModelProperty(value = "商家名称")
    private String providerName;
    @ApiModelProperty(value = "本地网标识")
    private String lanId;
    @ApiModelProperty(value = "区域标识")
    private String regionId;
    @ApiModelProperty(value = "区域名称")
    private String regionName;
}
