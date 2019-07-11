package com.iwhalecloud.retail.goods2b.dto.req;


import com.iwhalecloud.retail.goods2b.dto.GoodsRulesDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/****
 * @author gs_10010
 * @date 2019/7/9 20:23
 */
@Data
public class QueryProductObjReq implements Serializable {

    @ApiModelProperty("prodbaseid")
    private String productBaseId;

    private List<GoodsRulesDTO> dtoList;

    private String goodsId;

    private List<String> productIds;

    @ApiModelProperty("分货种类：1：按机型，2按规格")
    private String assignedType;

}
