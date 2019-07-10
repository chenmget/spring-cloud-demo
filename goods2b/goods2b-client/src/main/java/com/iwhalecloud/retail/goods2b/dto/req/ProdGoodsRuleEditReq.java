package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulePurchaseDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulesDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulesOperateDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/3/2.
 */
@Data
public class ProdGoodsRuleEditReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分货种类，1；机型，2：规格，默认按规格")
    private String assignType;

    @ApiModelProperty
    private List<GoodsRulesDTO> goodsRulesDTOList;

    private List<String> productIds;

    @ApiModelProperty
    private List<String> idList;

    @ApiModelProperty
    private String id;

    @ApiModelProperty
    private String goodsId;

    @ApiModelProperty
    List<GoodsRulesOperateDTO> goodsRulesOperateDTOList;

    @ApiModelProperty
    List<GoodsRulePurchaseDTO> goodsRulePurchaseDTOs;

}
