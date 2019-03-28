package com.iwhalecloud.retail.warehouse.dto.response.markres;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/2 14:49
 */
@Data
@ApiModel("查询零售商仓库终端的实例列表按多种条件返回参数")
public class QryMktInstInfoByConditionItemResp implements Serializable {
    @ApiModelProperty(value = "实例标识")
    private String mktresinstid;
    @ApiModelProperty(value = "串码")
    private String barcode;
    @ApiModelProperty(value = "资源标识")
    private String mktresid;
    @ApiModelProperty(value = "营销资源名称")
    private String mktresname;
    @ApiModelProperty(value = "仓库标识")
    private String storeid;
    @ApiModelProperty(value = "仓库名称")
    private String storename;
    @ApiModelProperty(value = "串码类型")
    private String currstate;
    @ApiModelProperty(value = "零售价格")
    private String price;
    @ApiModelProperty(value = "本地网标识")
    private String lanid;
    @ApiModelProperty(value = "本地网名称")
    private String lanname;
    @ApiModelProperty(value = "区域标识")
    private String regionid;
    @ApiModelProperty(value = "区域名称")
    private String regionname;
    @ApiModelProperty(value = "状态时间")
    private String statusdate;
    @ApiModelProperty(value = "状态")
    private String state;
    @ApiModelProperty(value = "入库时间")
    private String createdate;
    @ApiModelProperty(value = "商家标识")
    private String providercode;
    @ApiModelProperty(value = "商家名称")
    private String providername;
    @ApiModelProperty(value = "地市级供货商ID")
    private String citysupplyid;
    @ApiModelProperty(value = "地市级供货商名称")
    private String citysupplyname;
    @ApiModelProperty(value = "省级供货商ID")
    private String provsupplyid;
    @ApiModelProperty(value = "省级供货商名称")
    private String provsupplyname;
    @ApiModelProperty(value = "采购类型")
    private String purchasetype;

    @ApiModelProperty(value = "营销资源编码")
    private String mkt_res_nbr;
}
