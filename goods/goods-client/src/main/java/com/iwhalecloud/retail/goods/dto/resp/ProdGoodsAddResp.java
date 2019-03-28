package com.iwhalecloud.retail.goods.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/11/27
 */
@Data
@ApiModel(value = "添加商品返回结果")
public class ProdGoodsAddResp implements Serializable {

    private static final long serialVersionUID = -7679658958580776005L;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private String goodsId;
}
