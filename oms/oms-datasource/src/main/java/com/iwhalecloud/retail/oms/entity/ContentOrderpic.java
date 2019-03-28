package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ContentOrderpic
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_orderpic, 对应实体ContentOrderpic类")
@TableName("t_content_orderpic")
public class ContentOrderpic implements Serializable {
    /**表名常量*/
    public static final String TNAME = "t_content_orderpic";
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
  	 * playorder
  	 */
	@ApiModelProperty(value = "playorder")
  	private java.lang.Integer playorder;
  	
  	/**
  	 * objtype
  	 */
	@ApiModelProperty(value = "objtype")
  	private java.lang.Integer objtype;
  	
  	/**
  	 * objid
  	 */
	@ApiModelProperty(value = "objid")
  	private java.lang.String objid;
  	
  	/**
  	 * objurl
  	 */
	@ApiModelProperty(value = "objurl")
  	private java.lang.String objurl;
  	
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
        /** playorder */
        playorder,
        /** objtype */
        objtype,
        /** objid */
        objid,
        /** objurl */
        objurl,
        /** oprid */
        oprid,
        /** upddate */
        upddate
    }

	

}
