package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author z
 * @date 2019/3/8
 */
@Data
public class GoodsActRelListReq implements Serializable {

    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @ApiModelProperty(value = "活动类型")
    private String actType;
}
