package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ContentVediolv2
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_vediolv2, 对应实体ContentVediolv2类")
@TableName("t_content_vediolv2")
public class ContentVediolv2 implements Serializable {
    /**表名常量*/
    public static final String TNAME = "t_content_vediolv2";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * matid
  	 */
	@ApiModelProperty(value = "matid")
	@TableId(type = IdType.ID_WORKER)
  	private java.lang.Long matid;
  	
  	/**
  	 * upmatid
  	 */
	@ApiModelProperty(value = "upmatid")
  	private java.lang.Long upmatid;
  	
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
  	 * startsec
  	 */
	@ApiModelProperty(value = "startsec")
  	private java.lang.Integer startsec;
  	
  	/**
  	 * endsec
  	 */
	@ApiModelProperty(value = "endsec")
  	private java.lang.Integer endsec;
  	
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
        /** upmatid */
        upmatid,
        /** objtype */
        objtype,
        /** objid */
        objid,
        /** objurl */
        objurl,
        /** startsec */
        startsec,
        /** endsec */
        endsec,
        /** oprid */
        oprid,
        /** upddate */
        upddate
    }

	

}
