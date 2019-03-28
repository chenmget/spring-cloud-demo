package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * ContentMaterial
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_material, 对应实体ContentMaterial类")
@TableName("t_content_material")
public class ContentMaterial implements Serializable {
    /**表名常量*/
    public static final String TNAME = "t_content_material";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * matid
  	 */
	@ApiModelProperty(value = "matid")
	@TableId(type = IdType.ID_WORKER)
  	private java.lang.Long matid;

  	/**
  	 * contentid
  	 */
	@ApiModelProperty(value = "contentid")
  	private java.lang.Long contentid;
  	
  	/**
  	 * name
  	 */
	@ApiModelProperty(value = "name")
  	private java.lang.String name;
  	
  	/**
  	 * path
  	 */
	@ApiModelProperty(value = "path")
  	private java.lang.String path;
  	
  	/**
  	 * thumbpath
  	 */
	@ApiModelProperty(value = "thumbpath")
  	private java.lang.String thumbpath;
  	
  	/**
  	 * level
  	 */
	@ApiModelProperty(value = "lel")
  	private java.lang.Integer lel;
  	
  	/**
  	 * oprid
  	 */
	@ApiModelProperty(value = "oprid")
  	private java.lang.String oprid;
  	
  	/**
  	 * upddate
  	 */
	@ApiModelProperty(value = "upddate")
  	private java.util.Date upddate;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** matid */
        matid,
        /** contentid */
        contentid,
        /** name */
        name,
        /** path */
        path,
        /** thumbpath */
        thumbpath,
        /** level */
        level,
        /** oprid */
        oprid,
        /** upddate */
        upddate
    }

	

}
