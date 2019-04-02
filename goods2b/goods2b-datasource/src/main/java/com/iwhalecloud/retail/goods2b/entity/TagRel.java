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
 * ProdTagRel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("prod_tag_rel")
@KeySequence(value="seq_prod_tag_rel_id",clazz = String.class)
@ApiModel(value = "对应模型prod_tag_rel, 对应实体ProdTagRel类")
public class TagRel implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_tag_rel";
	private static final long serialVersionUID = 7754952843932505795L;

	//属性 begin
  	/**
  	 * relId
  	 */
	@TableId
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
	@ApiModelProperty(value = "productBaseId")
  	private String productBaseId;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** relId */
        relId,
        /** tagId */
        tagId,
        /** productBaseId */
		productBaseId
    }

	

}
