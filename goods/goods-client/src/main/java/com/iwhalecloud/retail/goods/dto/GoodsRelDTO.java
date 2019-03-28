package com.iwhalecloud.retail.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/11/23
 */
@Data
@ApiModel(value = "商品关系")
public class GoodsRelDTO implements Serializable {

    private static final long serialVersionUID = -5442192154054119897L;

    //属性 begin
    /**
     * A端商品id
     */
    @ApiModelProperty(value = "A端商品id")
    private String aGoodsId;

    /**
     * Z端商品id
     */
    @ApiModelProperty(value = "Z端商品id")
    private String zGoodsId;

    /**
     * 合约关联套餐CONTRACT_OFFER、终端关联购买计划TERMINAL_PLAN
     */
    @ApiModelProperty(value = "合约关联套餐CONTRACT_OFFER、终端关联购买计划TERMINAL_PLAN")
    private String relType;

    /**
     * relAttrInst
     */
    @ApiModelProperty(value = "relAttrInst")
    private String relAttrInst;

    /**
     * 设置购买计划关联合约信息按[{goods_id:合约idd,goods_name:合约名称},{},{}]格式存放
     */
    @ApiModelProperty(value = "设置购买计划关联合约信息按[{goods_id:合约idd,goods_name:合约名称},{},{}]格式存放")
    private String relContractInst;

    /**
     * sourceFrom
     */
    @ApiModelProperty(value = "sourceFrom")
    private String sourceFrom;

    /**
     * productId
     */
    @ApiModelProperty(value = "productId")
    private String productId;

    /**
     * 关联编码
     */
    @ApiModelProperty(value = "关联编码")
    private String relCode;
}
