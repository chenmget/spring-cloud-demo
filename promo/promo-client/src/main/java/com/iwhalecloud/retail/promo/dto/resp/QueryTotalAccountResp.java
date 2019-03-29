package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/26 11:32
 */
@Data
public class QueryTotalAccountResp implements Serializable {

    @ApiModelProperty(value = "账户ID")
    private String acctId;
    @ApiModelProperty(value = "账户名称")
    private String acctName;
    @ApiModelProperty(value = "账户类型")
    private String acctType;
    @ApiModelProperty(value = "可用余额笔数")
    private String balanceNum;
    @ApiModelProperty(value = "可用余额合计")
    private String totalAmount;
    @ApiModelProperty(value = "未生效余额合计")
    private String totalUneffAmount;
    @ApiModelProperty(value = "失效金额合计")
    private String totalInvaildAmount;
    @ApiModelProperty(value = "账户收入合计")
    private String totalIncome;
    @ApiModelProperty(value = "账户支出合计")
    private String totalExpenses;
}
