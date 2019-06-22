package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lhr 2019-03-13 15:21:30
 */
@Data
public class ActHistoryPurChaseUpReq extends AbstractPageReq implements Serializable{

    private static final long serialVersionUID = -5668416184732941868L;

    @ApiModelProperty("订单ID")
    private String orderId;

    @ApiModelProperty("更新时间")
    private Date gmtCreate;

}
