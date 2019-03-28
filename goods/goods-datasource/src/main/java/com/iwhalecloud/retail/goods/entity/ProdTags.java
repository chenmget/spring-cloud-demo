package com.iwhalecloud.retail.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdTags
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_tags, 对应实体ProdTags类")
@TableName("prod_tags")
public class ProdTags implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_tags";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * tagId
  	 */
	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "tagId")
  	private String tagId;
  	
  	/**
  	 * tagName
  	 */
	@ApiModelProperty(value = "tagName")
  	private String tagName;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** tagId */
        tagId,
        /** tagName */
        tagName
    }

	

}
