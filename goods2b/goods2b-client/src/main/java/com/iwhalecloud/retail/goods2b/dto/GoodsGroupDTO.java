package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/11/8
 **/
@Data
@ApiModel(value = "商品组组装数据，GoodsGroupDTO")
public class GoodsGroupDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 商品组ID
     */
    @ApiModelProperty(value = "商品组ID")
    private String groupId;
    /**
     * 商品组名称
     */
    @ApiModelProperty(value = "商品组名称")
    private String groupName;
    /**
     * 商品组编码
     */
    @ApiModelProperty(value = "商品组编码")
    private String groupCode;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String groupDesc;
    /**
     * 平台
     */
    @ApiModelProperty(value = "平台")
    private String sourceFrom;
    /**
     * 商品列表
     */
    @ApiModelProperty(value = "商品列表")
    private List<GoodsDTO>  goods;
    /**
     * 商品数
     */
    @ApiModelProperty(value = "商品数")
    private int goodsNum;
}
