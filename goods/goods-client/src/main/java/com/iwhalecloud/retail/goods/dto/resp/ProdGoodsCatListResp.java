package com.iwhalecloud.retail.goods.dto.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value = "对应模型PROD_GOODS_CAT, 对应实体ProdGoodsCat类")
public class ProdGoodsCatListResp implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private String catId;

    private String name;

    private BigDecimal parentId;

    private String catPath;

    private Integer catOrder;

}