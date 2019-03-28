package com.iwhalecloud.retail.web.controller.b2b.warehouse.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年03月11日
 * @Description:
 **/
@Data
@ApiModel(value = "库存预警查询入参")
public class InventoryWaringQueryReq implements Serializable {
    /**
     * 商家ID
     */
    @ApiModelProperty(value = "预警入参")
    @NotEmpty(message = "预警入参不能为空")
    private List<InventoryWaringQueryDTO> prodMerchantList;

}
