package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

/**
 * ContentText
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_text, 对应实体ContentText类")
@TableName("t_content_text")
public class ContentText implements Serializable {
    /**表名常量*/
    public static final String TNAME = "t_content_text";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * contentid
  	 */
	@ApiModelProperty(value = "contentid")
  	private java.lang.Long contentid;
  	
  	/**
  	 * textid
  	 */
    @TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "textid")
  	private java.lang.Long textid;
  	
  	/**
  	 * url
  	 */
	@ApiModelProperty(value = "url")
  	private java.lang.String url;
  	
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

	/**
	 * softtext
	 */
	@ApiModelProperty(value = "softtext")
	private java.lang.String softtext;
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** contentid */
        contentid,
        /** textid */
        textid,
        /** url */
        url,
        /** oprid */
        oprid,
        /** upddate */
        upddate,
        /** softtext */
        softtext
    }

	

}
