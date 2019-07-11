package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.goods2b.dto.GoodsProductRelDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulesDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/****
 * @author gs_10010
 * @date 2019/7/9 21:34
 */
@Data
public class CheckRuleReq implements Serializable {

    private List<GoodsRulesDTO> entityList;

    private List<GoodsProductRelDTO> goodsProductRelList;

    private String supplierId;

    @ApiModelProperty("分货方式：2按规格，1按机型")
    private String assignType;


    private List<String> targetList;
}
