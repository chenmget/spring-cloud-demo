package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.order2b.dto.resquest.order.AdvanceOrderReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SelectOrderDetailModel extends AdvanceOrderReq {

    @ApiModelProperty("订单状态")
    private List<String> statusAll;
    private List<String> orderList;

    private List<String> supperIds;

    private List<String> userIds;

    private List<String> lanIdList;


    private String createUserId;


    private String isDelete;

    @ApiModelProperty("商品品牌")
    private String brandName;
    @ApiModelProperty("25位编码")
    private String goodsSn;

}
