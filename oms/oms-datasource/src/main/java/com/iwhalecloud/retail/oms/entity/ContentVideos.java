package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * ContentVideos
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_video, 对应实体ContentVideosDefaultContent类")
@TableName("t_content_video")
public class ContentVideos implements Serializable {
    /**表名常量*/
    public static final String TNAME = "t_content_video";
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
  	 * havelv2mat
  	 */
	@ApiModelProperty(value = "havelv2mat")
  	private java.lang.Integer havelv2mat;
  	
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
	 * objurl
	 */
	@ApiModelProperty(value = "objurl")
	private java.lang.String objurl;
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** matid */
        matid,
        /** tccontentid */
        tccontentid,
        /** contentid */
        contentid,
        /** objtype */
        objtype,
        /** objid */
        objid,
        /** havelv2mat */
        havelv2mat,
        /** oprid */
        oprid,
        /** upddate */
        upddate,
		objurl
    }

	

}
