package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: wang.jiaxin
 * @date: 2019年03月26日
 * @description:
 **/
@Data
public class GoodsUpdateActTypeByGoodsIdsReq extends AbstractRequest implements Serializable {
    private static final long serialVersionUID = -8595535448012106101L;

    @ApiModelProperty(value = "商品ID列表")
    private List<String> goodsIds;

    @ApiModelProperty(value = "是否预售")
    private Integer isAdvanceSale;

    @ApiModelProperty(value = "是否前置补贴")
    private Integer isSubsidy;
}
