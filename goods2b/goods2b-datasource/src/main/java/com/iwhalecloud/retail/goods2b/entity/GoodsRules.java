package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("prod_goods_rules")
@ApiModel
@KeySequence(value="seq_prod_goods_rules_id",clazz = String.class)
public class GoodsRules implements Serializable {

    public static final String TNAME = "prod_goods_rules";

    @TableId(type=IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "关联ID")
    private String goodsRuleId;

    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "分货数量")
    private Long marketNum;

    @ApiModelProperty(value = "目标ID")
    private String targetId;

    @ApiModelProperty(value = "目标对象编码")
    private String targetCode;

    @ApiModelProperty(value = "目标对象名称")
    private String targetName;

    @ApiModelProperty(value = "目标对象类型,1-经营主体，2-零售商")
    private String targetType;

    @ApiModelProperty(value = "提货数量")
    private Long purchasedNum;

    @ApiModelProperty(value = "提货数量")
    private String state;

    public enum FieldNames{

        goodsRuleId("goodsRuleId","GOODS_RULE_ID"),
        goodsId("goodsId","GOODS_ID"),
        productId("productId","PRODUCT_ID"),
        productCode("productCode","PRODUCT_CODE"),
        productName("productName","PRODUCT_NAME"),
        targetId("targetId","TARGET_ID"),
        targetCode("targetCode","TARGET_CODE"),
        targetName("targetName","TARGET_NAME"),
        targetType("targetType","TARGET_TYPE"),
        marketNum("marketNum","MARKET_NUM"),
        purchasedNum("purchasedNum","PURCHASED_NUM"),
        state("state","STATE");

        private String fieldName;
        private String tableFieldName;

        FieldNames(String fieldName, String tableFieldName){
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
