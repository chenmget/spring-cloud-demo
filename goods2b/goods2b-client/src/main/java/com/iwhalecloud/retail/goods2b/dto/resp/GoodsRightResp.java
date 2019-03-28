package com.iwhalecloud.retail.goods2b.dto.resp;

import com.iwhalecloud.retail.goods2b.dto.RightDTO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/2/23.
 */
public class GoodsRightResp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品ID：goodsId
     */
    @ApiModelProperty(value = "goodsId")
    private java.lang.String goodsId;

    /**
     * 权益列表：rightList
     */
    @ApiModelProperty(value = "rightsId")
    private List<RightDTO> rightsList;

}
