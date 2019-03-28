package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.goods2b.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by he.sw on 2018/12/24.
 */
@Data
public class ProductBaseListByProdIdsReq extends PageVO{

    private static final long serialVersionUID = 1L;

    /**
     * productBaseListReq
     */
    @ApiModelProperty(value = "productBaseListReq")
    private ProductBaseListReq productBaseListReq;

    /**
     * 产品ID集合
     */
    @ApiModelProperty(value = "产品ID集合")
    private List<String> productIdList;
}
