package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author li.yulong
 * @date 2019/3/6
 */
@Data
@ApiModel("根据补录记录ID，查询活动补录记录详情入参")
public class ActSupDetailReq  extends AbstractPageReq implements Serializable {

    private static final long serialVersionUID = -8599760751415895330L;
    /**
     * 补录记录ID
     */
    @ApiModelProperty(value = "补录记录ID")
    private String recordId;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "串码")
    private String resNbr;
}
