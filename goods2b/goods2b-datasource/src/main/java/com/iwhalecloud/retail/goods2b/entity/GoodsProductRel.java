package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * GoodsProductRel
 *
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("prod_goods_product_rel")
@ApiModel(value = "对应模型prod_goods_product_rel, 对应实体GoodsProductRel类")
@KeySequence(value = "seq_prod_goods_product_rel_id", clazz = String.class)
public class GoodsProductRel implements Serializable {
    /**
     * 表名常量
     */
    public static final String TNAME = "prod_goods_product_rel";
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 关联ID
     */
    @TableId
    @ApiModelProperty(value = "关联ID")
    private String goodsProductRelId;

    /**
     * 产品基本信息ID
     */
    @ApiModelProperty(value = "产品基本信息ID")
    private String productBaseId;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private String productId;

    /**
     * 上架数量
     */
    @ApiModelProperty(value = "上架数量")
    private Long supplyNum;

    /**
     * 提货价
     */
    @ApiModelProperty(value = "提货价")
    private Double deliveryPrice;

    /**
     * 最小起批量
     */
    @ApiModelProperty(value = "最小起批量")
    private Long minNum;

    /**
     * 订购上限
     */
    @ApiModelProperty(value = "订购上限")
    private Long maxNum;

    /**
     * 规格别名
     */
    @ApiModelProperty(value = "规格别名")
    private String specName;

    /**
     * 是否有库存
     */
    @ApiModelProperty(value = "是否有库存")
    private Integer isHaveStock;

    /**
     * 活动预付款，如预售活动的定金。冗余用字段
     */
    @ApiModelProperty(value = "活动预付款，如预售活动的定金。冗余用字段")
    private Long advancePayAmount;

    /**
     * 初始提货价
     */
    @ApiModelProperty(value = "初始提货价")
    private Double initialPrice;


    //属性 end

    public static enum FieldNames {
        /**
         * 关联ID
         */
        goodsProductRelId("goodsProductRelId", "GOODS_PRODUCT_REL_ID"),
        /**
         * 产品基本信息ID
         */
        productBaseId("productBaseId", "PRODUCT_BASE_ID"),
        /**
         * 商品ID
         */
        goodsId("goodsId", "GOODS_ID"),
        /**
         * 产品ID
         */
        productId("productId", "PRODUCT_ID"),
        /**
         * 上架数量
         */
        supplyNum("supplyNum", "SUPPLY_NUM"),
        /**
         * 提货价
         */
        deliveryPrice("deliveryPrice", "DELIVERY_PRICE"),
        /**
         * 最小起批量
         */
        minNum("minNum", "MIN_NUM"),
        /**
         * 订购上限
         */
        maxNum("maxNum", "MAX_NUM"),
        /**
         * 规格别名
         */
        specName("specName", "SPEC_NAME"),

        /**
         * 是否有库存
         */
        isHaveStock("isHaveStock", "IS_HAVE_STOCK"),
        /**
         * 活动预付款，如预售活动的定金。冗余用字段
         */
        advancePayAmount("advancePayAmount", "ADVANCE_PAY_AMOUNT"),
        /**
         * 初始提货价
         */
        initialPrice("initialPrice", "INITIAL_PRICE");

        private String fieldName;
        private String tableFieldName;

        FieldNames(String fieldName, String tableFieldName) {
            this.fieldName = fieldName;
            this.tableFieldName = tableFieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getTableFieldName() {
            return tableFieldName;
        }
    }


}
