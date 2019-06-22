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
@ApiModel(value = "对应模型es_goods_group, 对应实体GoodsRel类")
@TableName("prod_goods_group")
@KeySequence(value="seq_prod_goods_group_id",clazz = String.class)
public class GoodsGroup implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_goods_group";
    private static final long serialVersionUID = -3546168352276596516L;
    /**
     * 商品组ID
     */
    @ApiModelProperty(value = "商品组ID")
    @TableId
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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.sql.Timestamp createTime;
}
