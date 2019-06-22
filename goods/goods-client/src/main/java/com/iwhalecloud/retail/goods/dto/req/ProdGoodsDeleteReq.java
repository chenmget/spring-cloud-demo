package com.iwhalecloud.retail.goods.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/11/27
 */
@Data
public class ProdGoodsDeleteReq implements Serializable {

    /**
     * 商品id
     */
    @NotBlank
    @ApiModelProperty(value = "商品id")
    private String goodsId;
}
