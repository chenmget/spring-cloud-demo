package com.iwhalecloud.retail.warehouse.dto.response.markres;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/2 15:09
 */
@Data
@ApiModel("查询零售商仓库终端的库存数量按条件返回参数")
public class StoreInventoryQuantityItemResp  implements Serializable {

    @ApiModelProperty(value = "资源标识")
    private String mktresid;
    @ApiModelProperty(value = "营销资源名称")
    private String mktresname;
    @ApiModelProperty(value = "仓库标识")
    private String storeid;
    @ApiModelProperty(value = "仓库名称")
    private String storename;
    @ApiModelProperty(value = "库存数量")
    private String storenum;
    @ApiModelProperty(value = "商家编码")
    private String provider;
    @ApiModelProperty(value = "商家名称")
    private String providername;
    @ApiModelProperty(value = "本地网标识")
    private String lanid;
    @ApiModelProperty(value = "区域标识")
    private String regionid;
    @ApiModelProperty(value = "区域名称")
    private String regionname;
}
