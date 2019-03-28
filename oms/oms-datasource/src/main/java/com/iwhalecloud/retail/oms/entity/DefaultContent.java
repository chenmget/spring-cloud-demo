package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 默认内容详情
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("DEFAULT_CONTENT")
@ApiModel(value = "对应模型DEFAULT_CONTENT, 对应实体DefaultContent类")
public class DefaultContent implements Serializable {
    /**表名常量*/
    public static final String TNAME = "DEFAULT_CONTENT";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ID
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "ID")
  	private java.lang.Long id;
  	
  	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date gmtCreate;
  	
  	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date gmtModified;
  	
  	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private java.lang.String creator;
  	
  	/**
  	 * 修改人	
  	 */
	@ApiModelProperty(value = "修改人	")
  	private java.lang.String modifier;
  	
  	/**
  	 * 是否删除：0未删、1删除	
  	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除	")
  	private java.lang.Long isDeleted;
  	
  	/**
  	 * 内容名称	
  	 */
	@ApiModelProperty(value = "内容名称	")
  	private java.lang.String contentTittle;
  	
  	/**
  	 * 内容标签	
  	 */
	@ApiModelProperty(value = "内容标签	")
  	private java.lang.String contentTips;
  	
  	/**
  	 * 内容关键字	
  	 */
	@ApiModelProperty(value = "内容关键字	")
  	private java.lang.String contentKeyword;
  	
  	/**
  	 * 内容简介	
  	 */
	@ApiModelProperty(value = "内容简介	")
  	private java.lang.String contentBrief;
  	
  	/**
  	 * 内容详情	
  	 */
	@ApiModelProperty(value = "内容详情	")
  	private java.lang.String contentDetail;
  	
  	/**
  	 * 内容图片	
  	 */
	@ApiModelProperty(value = "内容图片	")
  	private java.lang.String contentPictures;
  	
  	/**
  	 * 内容扩展属性	
  	 */
	@ApiModelProperty(value = "内容扩展属性	")
  	private java.lang.String contentExtensionInfo;
  	
  	
  	//属性 end
	
	  
  	public static enum FieldNames{
        /** ID */
        id,
        /** 创建时间 */
        gmtCreate,
        /** 修改时间 */
        gmtModified,
        /** 创建人 */
        creator,
        /** 修改人	 */
        modifier,
        /** 是否删除：0未删、1删除	 */
        isDeleted,
        /** 内容名称	 */
        contentTittle,
        /** 内容标签	 */
        contentTips,
        /** 内容关键字	 */
        contentKeyword,
        /** 内容简介	 */
        contentBrief,
        /** 内容详情	 */
        contentDetail,
        /** 内容图片	 */
        contentPictures,
        /** 内容扩展属性	 */
        contentExtensionInfo
    }

	

}
