package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * ContentPublish
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_publish, 对应实体ContentPublish类")
@TableName("t_content_publish")
public class ContentPublish implements Serializable {
    /**表名常量*/
    public static final String TNAME = "t_content_publish";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID")
	@TableId(type = IdType.ID_WORKER)
	private java.lang.Long Id;

  	/**
  	 * contentid
  	 */
	@ApiModelProperty(value = "contentid")
  	private java.lang.Long contentid;
  	
  	/**
  	 * waytype
  	 */
	@ApiModelProperty(value = "waytype")
  	private java.lang.String waytype;
  	
  	/**
  	 * area
  	 */
	@ApiModelProperty(value = "area")
  	private java.lang.String area;
  	
  	/**
  	 * oprid
  	 */
	@ApiModelProperty(value = "oprid")
  	private java.lang.String oprid;
  	
  	/**
  	 * publishdate
  	 */
	@ApiModelProperty(value = "publishdate")
  	private java.util.Date publishdate;
  	
  	/**
  	 * effdate
  	 */
	@ApiModelProperty(value = "effdate")
  	private java.util.Date effdate;
  	
  	/**
  	 * expdate
  	 */
	@ApiModelProperty(value = "expdate")
  	private java.util.Date expdate;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** contentid */
        contentid,
        /** waytype */
        waytype,
        /** area */
        area,
        /** oprid */
        oprid,
        /** publishdate */
        publishdate,
        /** effdate */
        effdate,
        /** expdate */
        expdate
    }

	

}
