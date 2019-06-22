package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/26 11:30
 */
@Data
public class QueryTotalAccountReq extends AbstractPageReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户类型")
    private String acctType;
    @ApiModelProperty(value = "商家标识")
    private String custId;


}
