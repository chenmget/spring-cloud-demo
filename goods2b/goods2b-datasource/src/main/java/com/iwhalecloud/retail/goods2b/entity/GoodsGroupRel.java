package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/5
 **/
@Data
@ApiModel(value = "对应模型es_goods_group_rel, 对应实体GoodsRel类")
@TableName("prod_goods_group_rel")
@KeySequence(value="seq_prod_goods_group_rel_id",clazz = String.class)
public class GoodsGroupRel implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_goods_group_rel";
    private static final long serialVersionUID = -3546168352276596516L;
    /**
     *关联ID
     */
    @ApiModelProperty(value = "关联ID")
    @TableId
    private String groupRelId;
    /**
     *商品id
     */
    @ApiModelProperty(value = "商品id")
    private String goodsId;
    /**
     *商品组id
     */
    @ApiModelProperty(value = "商品组id")
    private String groupId;
}
