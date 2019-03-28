package com.iwhalecloud.retail.goods2b.dto.resp;

import com.iwhalecloud.retail.goods2b.dto.CatDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "对应模型PROD_GOODS_CAT, 对应实体ProdGoodsCat类")
public class CatListResp implements Serializable{
	
	private static final long serialVersionUID = 1L;
    /**
     * 类别列表
     */
    private List<CatDTO> prodCatList;

}