package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/2/27.
 */
@Data
@ApiModel(value = "对应模型prod_goods_complex_info, 对应实体prod_goods_complex_info类")
@TableName("prod_goods_complex_info")
@KeySequence(value="seq_prod_goods_complex_info_id",clazz = String.class)
public class ComplexInfo implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_goods_complex_info";
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * commentId
     */
    @TableId
    @ApiModelProperty(value = "complexInfoId")
    private java.lang.String complexInfoId;

    /**
     * forCommentId
     */
    @ApiModelProperty(value = "aGoodsId")
    private java.lang.String aGoodsId;

    /**
     * 关联ID 商品ID
     */
    @ApiModelProperty(value = "zGoodsId")
    private java.lang.String zGoodsId;

    /**
     * 消息类型
     */
    @ApiModelProperty(value = "complexInfo")
    private java.lang.String complexInfo;

    //属性 end

    public static enum FieldNames{
        /** complexInfoId */
        complexInfoId,
        /** aGoodsId */
        aGoodsId,
        /** zGoodsId */
        zGoodsId,
        /** complexName */
        complexInfo
    }
}
