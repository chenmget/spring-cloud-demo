package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/3/27 11:38
 */
@Data
public class QueryAccountBalanceDetailAllReq  extends QueryAccountIncomeDetailReq implements Serializable {
    @ApiModelProperty(value = "卖家ID集合")
    private List<String> supplierIdList;
    @ApiModelProperty(value = "活动ID集合")
    private List<String> actIdList;
}
