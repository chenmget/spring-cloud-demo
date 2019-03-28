package com.iwhalecloud.retail.order.dto.resquest;

import com.iwhalecloud.retail.order.dto.model.SendGoodsItemModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderInfoEntryRequest extends UpdateOrderStatusRequest implements Serializable {

    @ApiModelProperty(value = "候补单号:合约机必填")
    private String afterOrder;

    @ApiModelProperty(value = "商品信息")
    private List<SendGoodsItemModel> item;

    @ApiModelProperty(value = "备注")
    private String remark;
}
