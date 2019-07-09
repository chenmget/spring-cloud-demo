package com.iwhalecloud.retail.order2b.dto.resquest.promo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "订单优惠列表查询对象")
public class PromotionListReq implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "lanId")
    private String lanId;

    @ApiModelProperty(value = "ordPromotioId")
    private String ordPromotioId;

    @ApiModelProperty(value = "orderId")
    private String orderId;

    @ApiModelProperty(value = "orderItemId")
    private String orderItemId;

    @ApiModelProperty(value = "lanIdList")
    private List<String> lanIdList;

    @ApiModelProperty(value = "orderIdList")
    private List<String> orderIdList;

    @ApiModelProperty(value = "mktActName")
    private String mktActName;

    @ApiModelProperty(value = "mktActType")
    private String mktActType;
}
