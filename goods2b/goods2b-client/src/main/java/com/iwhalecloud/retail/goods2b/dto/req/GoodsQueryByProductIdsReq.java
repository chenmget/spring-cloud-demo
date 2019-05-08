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
public class GoodsQueryByProductIdsReq extends AbstractRequest implements Serializable {
    private static final long serialVersionUID = -257960651077625218L;

    @ApiModelProperty(value = "产品ID列表")
    private List<String> productIds;
}
