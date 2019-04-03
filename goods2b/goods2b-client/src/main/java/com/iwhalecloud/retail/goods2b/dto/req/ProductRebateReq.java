package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/30 15:18
 */
@Data
public class ProductRebateReq implements Serializable {
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "品牌")
    private String brandName;
    @ApiModelProperty(value = "产品型号")
    private String unitType;
}
