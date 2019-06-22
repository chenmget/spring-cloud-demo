package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/3/27 16:37
 * 返利余额查询服务
 */
@Data
public class QueryAccountBalanceAllReq  extends AbstractPageReq implements Serializable {

    @ApiModelProperty(value = "商家ID")
    private String custId;
    @ApiModelProperty(value = "账户类型")
    private String acctType;
    @ApiModelProperty(value = "账户ID")
    private String acctId;
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;
    @ApiModelProperty(value = "生效时间起")
    private String effDateStart;
    @ApiModelProperty(value = "生效时间止")
    private String effDateEnd;


    private List<String> balanceTypeIdList;


}
