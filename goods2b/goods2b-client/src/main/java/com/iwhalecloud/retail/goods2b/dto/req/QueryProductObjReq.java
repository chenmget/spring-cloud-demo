package com.iwhalecloud.retail.goods2b.dto.req;


import com.iwhalecloud.retail.goods2b.dto.GoodsRulesProductDTO;
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

    private List<GoodsRulesProductDTO> dtoList;

}
