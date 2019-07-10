package com.iwhalecloud.retail.goods2b.model;

import com.iwhalecloud.retail.goods2b.dto.GoodsProductRelDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulesDTO;
import lombok.Data;

import java.util.List;

/****
 * @author gs_10010
 * @date 2019/7/9 21:34
 */
@Data
public class CheckRuleModel {

    private List<GoodsRulesDTO> entityList;

    private List<GoodsProductRelDTO> goodsProductRelList;

    private String supplierId;

    private String disProductType;
}
