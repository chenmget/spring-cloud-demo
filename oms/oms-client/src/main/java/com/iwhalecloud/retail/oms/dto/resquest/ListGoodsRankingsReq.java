package com.iwhalecloud.retail.oms.dto.resquest;

import com.iwhalecloud.retail.oms.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品排行查询
 * @author generator
 * @version 1.0
 * @since 1.0
 */

@Data
@ApiModel(value = "商品排行查询")
public class ListGoodsRankingsReq extends PageVO {

    private static final long serialVersionUID = 1L;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private String goodsId;
    
    /**
     * 业务类型
     */
    @ApiModelProperty(value = "业务类型")
    private String objType;
}
