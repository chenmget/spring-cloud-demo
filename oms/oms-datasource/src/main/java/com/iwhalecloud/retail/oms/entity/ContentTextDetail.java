package com.iwhalecloud.retail.oms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 软文内容详情表
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("T_CONTENT_TEXT_DETAIL")
@ApiModel(value = "对应模型T_CONTENT_TEXT_DETAIL, 对应实体ContentTextDetail类")
public class ContentTextDetail implements Serializable {
    /**表名常量*/
    public static final String TNAME = "T_CONTENT_TEXT_DETAIL";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ID
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "ID")
  	private Long id;
  	
  	/**
  	 * 软文ID
  	 */
	@ApiModelProperty(value = "软文ID")
  	private Long textId;
  	
  	/**
  	 * 软文内容ID
  	 */
	@ApiModelProperty(value = "软文内容ID")
  	private Long txtContentId;
  	
  	/**
  	 * 软文内容类型
  	 */
	@ApiModelProperty(value = "软文内容类型")
  	private Integer txtContentType;
  	
  	/**
  	 * 软文内容序列
  	 */
	@ApiModelProperty(value = "软文内容序列")
  	private Integer txtContentSeq;
  	
  	/**
  	 * 软文内容值
  	 */
	@ApiModelProperty(value = "软文内容值")
  	private String txtContentData;
  	
  	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date gmtCreat;
  	
  	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date gmtModify;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** ID. */
		id("id","ID"),
		
		/** 软文ID. */
		textId("textId","TEXT_ID"),
		
		/** 软文内容ID. */
		txtContentId("txtContentId","TXT_CONTENT_ID"),
		
		/** 软文内容类型. */
		txtContentType("txtContentType","TXT_CONTENT_TYPE"),
		
		/** 软文内容序列. */
		txtContentSeq("txtContentSeq","TXT_CONTENT_SEQ"),
		
		/** 软文内容值. */
		txtContentData("txtContentData","TXT_CONTENT_DATA"),
		
		/** 创建时间. */
		gmtCreat("gmtCreat","GMT_CREAT"),
		
		/** 修改时间. */
		gmtModify("gmtModify","GMT_MODIFY");

		private String fieldName;
		private String tableFieldName;
		FieldNames(String fieldName, String tableFieldName){
			this.fieldName = fieldName;
			this.tableFieldName = tableFieldName;
		}

		public String getFieldName() {
			return fieldName;
		}

		public String getTableFieldName() {
			return tableFieldName;
		}
	}

}
