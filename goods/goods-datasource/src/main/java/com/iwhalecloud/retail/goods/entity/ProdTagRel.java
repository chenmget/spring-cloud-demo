package com.iwhalecloud.retail.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "对应模型prod_tag_rel, 对应实体ProdTagRel类")
public class ProdTagRel implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_tag_rel";
  	private static final long serialVersionUID = 1L;
  
  	
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
	@ApiModelProperty(value = "goodsId")
  	private String goodsId;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** relId */
        relId,
        /** tagId */
        tagId,
        /** goodsId */
        goodsId
    }

	

}
