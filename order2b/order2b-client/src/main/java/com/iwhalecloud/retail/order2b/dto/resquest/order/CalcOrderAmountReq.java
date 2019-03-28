package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.dto.model.order.SelectedCouponDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/2/28 16:45
 * @description
 */
@Data
public class CalcOrderAmountReq extends PreCreateOrderReq implements Serializable {


    @ApiModelProperty("已选中优惠券列表")
    private List<SelectedCouponDTO> selectedCouponList;
}

