package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/12/24
 */
@Data
@ApiModel(value = "添加商品返回结果")
public class GoodsAddResp implements Serializable {

    private static final long serialVersionUID = 9044928574771270954L;
    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private String goodsId;
}
