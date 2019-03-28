package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mzl
 * @date 2018/11/29
 */
@Data
public class ProdGoodsListReq implements Serializable {

    private static final long serialVersionUID = 8666487745866884569L;

    /**
     * 商品ID列表
     */
    @ApiModelProperty(value = "商品ID列表")
    private List<String> ids;

    /**
     * 商品分类id
     */
    @ApiModelProperty(value = "商品分类id")
    private String catId;

    /**
     * 关键词搜索
     */
    @ApiModelProperty(value = "关键词搜索")
    private String searchKey;

    /**
     * 上下架状态
     */
    @ApiModelProperty(value = "上下架状态")
    private String marketEnable;
}
