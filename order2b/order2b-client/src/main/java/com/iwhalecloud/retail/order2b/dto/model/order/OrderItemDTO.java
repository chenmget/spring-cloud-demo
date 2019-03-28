package com.iwhalecloud.retail.order2b.dto.model.order;

import com.iwhalecloud.retail.order2b.dto.base.SelectModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemDTO extends SelectModel implements Serializable {
    @ApiModelProperty("")
    private String itemId;
    private String orderId;
    private String goodsId;
    private Integer num;
    @ApiModelProperty("发货数量")
    private Integer deliveryNum;
    @ApiModelProperty("收货数量")
    private Integer receiveNum;
    @ApiModelProperty("退货数量")
    private Integer returnNum;
    @ApiModelProperty("换货数量")
    private Integer replaceNum;
    private String goodsName;
    private String productName;
    @ApiModelProperty("价格")
    private Double price;
    @ApiModelProperty("优惠金额")
    private Double couponPrice;
    private String unit;
    private String sourceFrom;
    private String itemType;
    private String productId;
    private String createTime;
    @ApiModelProperty("规格id")
    private String specId;
    @ApiModelProperty("规格描述")
    private String specDesc;
    @ApiModelProperty("图片")
    private String image;


}
