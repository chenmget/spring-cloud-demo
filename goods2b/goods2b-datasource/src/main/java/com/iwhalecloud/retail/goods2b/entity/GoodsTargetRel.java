package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/11.
 */
@Data
@TableName("prod_goods_target_rel")
@KeySequence(value="seq_prod_goods_target_rel_id",clazz = String.class)
@ApiModel(value = "对应模型prod_goods_region_rel, 对应实体GoodsRegionRel类")
public class GoodsTargetRel implements Serializable {

    /**表名常量*/
    public static final String TNAME = "prod_goods_target_rel";
    private static final long serialVersionUID = 1L;

    //属性 begin
    /**
     * relId
     */
    @TableId
    @ApiModelProperty(value = "goodsRelId")
    private String goodsRelId;

    /**
     * goodsId
     */
    @ApiModelProperty(value = "goodsId")
    private String goodsId;

    /**
     * regionId
     */
    @ApiModelProperty(value = "targetId")
    private String targetId;


    //属性 end

    public static enum FieldNames{
        /** goodsRelId */
        goodsRelId,
        /** goodsId */
        goodsId,
        /** targetId */
        targetId
    }


}
