package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/3/30 14:04
 */
@Data
public class QueryAccountBalancePayoutReq extends AbstractPageReq implements Serializable {

    @ApiModelProperty(value = "返利使用时间开始")
    private String operDateStart;
    @ApiModelProperty(value = "返利使用时间结束")
    private String operDateEnd;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "品牌")
    private String brandName;
    @ApiModelProperty(value = "产品型号")
    private String unitType;
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    @ApiModelProperty(value = "供应商账号")
    private String supplierLoginName;
    @ApiModelProperty(value = "商家id")
    private String custId;

    private List<String> productIdList;
    private List<String> supplierIdList;
}
