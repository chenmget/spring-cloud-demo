package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年02月27日
 * @Description:
 **/
@Data
@TableName("prod_tag_tel")
@ApiModel(value = "对应模型prod_tag_tel, 对应实体TagTel类")
public class TagTel implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_tag_tel";
    private static final long serialVersionUID = 749063506753607161L;

    //属性 begin
    /**
     * relId
     */
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "relId")
    private String relId;

    /**
     * tagId
     */
    @ApiModelProperty(value = "tagId")
    private String tagId;

    /**
     * goodsId
     */
    @ApiModelProperty(value = "productId")
    private String productId;


    //属性 end

    public static enum FieldNames{
        /** relId */
        relId,
        /** tagId */
        tagId,
        /** goodsId */
        productId
    }
}
