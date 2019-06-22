package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdKeywords
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("PROD_KEYWORDS")
@KeySequence(value="seq_prod_keywords_id",clazz = String.class)
@ApiModel(value = "对应模型PROD_KEYWORDS, 对应实体ProdKeywords类")
public class ProdKeywords implements Serializable {
    /**表名常量*/
    public static final String TNAME = "PROD_KEYWORDS";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 关键字ID
  	 */
	@TableId
	@ApiModelProperty(value = "关键字ID")
  	private String keywordsId;
  	
  	/**
  	 * 记录搜索关键字
  	 */
	@ApiModelProperty(value = "记录搜索关键字")
  	private String keywords;
  	
  	/**
  	 * 记录搜索次数
  	 */
	@ApiModelProperty(value = "记录搜索次数")
  	private Long searchNum;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 关键字ID. */
		keywordsId("keywordsId","KEYWORDS_ID"),
		
		/** 记录搜索关键字. */
		keywords("keywords","KEYWORDS"),
		
		/** 记录搜索次数. */
		searchNum("searchNum","SEARCH_NUM");

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
