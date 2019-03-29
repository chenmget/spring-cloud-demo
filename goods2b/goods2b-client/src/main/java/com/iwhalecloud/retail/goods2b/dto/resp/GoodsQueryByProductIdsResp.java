package com.iwhalecloud.retail.goods2b.dto.resp;

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
public class GoodsQueryByProductIdsResp extends AbstractRequest implements Serializable {
    private static final long serialVersionUID = 6317161536454754070L;

    @ApiModelProperty(value = "商品ID列表")
    private List<String> goodsIds;
}
