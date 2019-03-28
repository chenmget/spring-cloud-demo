package com.iwhalecloud.retail.web.controller.rfid.request;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Z
 * @date 2018/11/30
 */
public class RfidGoodsRelAddReq {

    @NotEmpty(message = "rfid不能为空")
    @ApiModelProperty(value = "rfid")
    private String rfid;
    @NotEmpty(message = "商品ID不能为空")
    @ApiModelProperty(value = "商品ID")
    private String goodsId;
    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    @ApiModelProperty(value = "产品ID")
    private String productId;
    @ApiModelProperty(value = "门店ID")
    private String shopId;
}
