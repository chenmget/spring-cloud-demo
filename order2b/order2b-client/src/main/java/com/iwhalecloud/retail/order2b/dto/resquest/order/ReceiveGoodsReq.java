package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.dto.model.order.SendGoodsItemDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ReceiveGoodsReq extends UpdateOrderStatusRequest implements Serializable {


    private  List<SendGoodsItemDTO> list;

    @ApiModelProperty("申请单号")
    private String orderApplyId;

    @ApiModelProperty("正常收货：1，换货收货：3")
    private String type;
}
