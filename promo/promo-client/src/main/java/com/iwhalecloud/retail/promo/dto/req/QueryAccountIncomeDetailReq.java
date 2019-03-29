package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/3/26 20:27
 */
@Data
public class QueryAccountIncomeDetailReq extends AbstractPageReq implements Serializable {
    @ApiModelProperty(value = "商家ID")
    private String custId;
    @ApiModelProperty(value = "账户类型")
    private String acctType;

    @ApiModelProperty(value = "账户ID")
    private String acctId;
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    @ApiModelProperty(value = "供应商账号")
    private String supplierLoginName;
    @ApiModelProperty(value = "状态")
    private String statucCd;
    @ApiModelProperty(value = "生效时间起")
    private String effDateStart;
    @ApiModelProperty(value = "生效时间止")
    private String effDateEnd;
    @ApiModelProperty(value = "失效时间起")
    private String expDateStart;
    @ApiModelProperty(value = "失效时间止")
    private String expDateEnd;
    @ApiModelProperty(value = "活动名称")
    private String actName;
    @ApiModelProperty(value = "来源类型")
    private String balanceSourceTypeId;


}
