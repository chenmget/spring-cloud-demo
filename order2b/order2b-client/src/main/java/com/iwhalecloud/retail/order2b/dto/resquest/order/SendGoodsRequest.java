package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.dto.model.order.SendGoodsItemDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SendGoodsRequest extends UpdateOrderStatusRequest implements Serializable {

    @ApiModelProperty(value = "物流单号")
    private String logiNo;
    @ApiModelProperty(value = "物流公司")
    private String logiName;
    @ApiModelProperty(value = "物流ID")
    private String logiId;
    @ApiModelProperty(value = "提货码")
    private String getCode;
    @ApiModelProperty(value = "发货数量")
    private Integer shipNum;

    private List<SendGoodsItemDTO> goodsItemDTOList;


    @ApiModelProperty("申请单号")
    private String orderApplyId;

    @ApiModelProperty("正常发货：1，换货发货：3")
    private String type;

    @ApiModelProperty("商家Id")
    private String merchantId;

    /**
     * 串码id
     */
    private List<String> resNbrList;


}
