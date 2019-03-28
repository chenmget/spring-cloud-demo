package com.iwhalecloud.retail.order2b.dto.model.order;

import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.dto.base.SelectModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemDetailDTO extends SelectModel implements Serializable {

    private String detailId;
    private String itemId;
    private String goodsId;
    private String productId;
    @ApiModelProperty("串码")
    private String resNbr;
    private Integer batchId;
    private String createUserId;
    private String createTime;
    private String  state;
    private String  stateName;
    private String goodsName;
    private String productName;
    @ApiModelProperty("申请单号")
    private String orderApplyId;

    public String getCreateTime() {
        return timeFormat(createTime);
    }

    public String getStateName() {
        return OrderAllStatus.matchOpCode(state).getName();
    }


}
