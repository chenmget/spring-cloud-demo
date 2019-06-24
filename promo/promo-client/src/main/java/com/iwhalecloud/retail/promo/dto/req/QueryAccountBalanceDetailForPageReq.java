package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/26 16:02
 */
@Data
public class QueryAccountBalanceDetailForPageReq extends AbstractPageReq implements Serializable {
    @ApiModelProperty("账户ID")
    private String acctId;
    @ApiModelProperty("余额账本标识")
    private String accountBalanceId;
    @ApiModelProperty("订单项ID")
    private String orderItemId;

}
