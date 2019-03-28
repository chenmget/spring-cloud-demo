package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * ContentTag
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_tag, 对应实体ContentTag类")
@TableName("t_content_tag")
public class ContentTag implements Serializable {
    /**表名常量*/
    public static final String TNAME = "t_content_tag";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 关联ID
  	 */
	@ApiModelProperty(value = "关联ID")
	@TableId(type = IdType.ID_WORKER)
  	private java.lang.Long relaId;
  	
  	/**
  	 * 内容ID
  	 */
	@ApiModelProperty(value = "内容ID")
  	private java.lang.Long contentId;
  	
  	/**
  	 * 标签ID
  	 */
	@ApiModelProperty(value = "标签ID")
  	private java.lang.Long tagId;
  	
  	/**
  	 * 操作人
  	 */
	@ApiModelProperty(value = "操作人")
  	private java.lang.String oprId;
  	
  	/**
  	 * 操作时间	
  	 */
	@ApiModelProperty(value = "操作时间	")
  	private java.util.Date oprDate;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** 关联ID */
        relaId,
        /** 内容ID */
        contentId,
        /** 标签ID */
        tagId,
        /** 操作人 */
        oprId,
        /** 操作时间	 */
        oprDate
    }

	

}
