package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/2/23.
 */
@Data
@ApiModel(value = "对应模型Goods_right, 对应实体GoodRight类")
@KeySequence(value="seq_prod_right_id",clazz = String.class)
@TableName("prod_right")
public class GoodsRight implements Serializable {

    /**表名常量*/
    public static final String TNAME = "prod_right";
    private static final long serialVersionUID = 1L;

    //属性 begin
    /**
     * goodsRightsId
     */
    @TableId
    @ApiModelProperty(value = "goodsRightsId")
    private java.lang.String goodsRightsId;

    /**
     * goodsId
     */
    @ApiModelProperty(value = "goodsId")
    private java.lang.String goodsId;

    /**
     * rightsId
     */
    @ApiModelProperty(value = "rightsId")
    private java.lang.String rightsId;

    /**
     * rightsName
     */
    @ApiModelProperty(value = "rightsName")
    private java.lang.String rightsName;

    /**
     * status
     */
    @ApiModelProperty(value = "status")
    private java.lang.String status;


    public static enum FieldNames{
        /** goodsRightsId */
        goodsRightsId,
        /** goodsId */
        goodsId,
        /** rightsId */
        rightsId,
        /** rightsName*/
        rightsName,
        /** status */
        status
    }
}
