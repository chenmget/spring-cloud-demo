package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * CatComplex
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_cat_complex, 对应实体CatComplex类")
@KeySequence(value="seq_prod_cat_complex_id",clazz = String.class)
@TableName("prod_cat_complex")
public class CatComplex implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_cat_complex";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * id
  	 */
	@TableId
	@ApiModelProperty(value = "id")
  	private String id;
  	
  	/**
  	 * catId
  	 */
	@ApiModelProperty(value = "catId")
  	private String catId;
  	
  	/**
  	 * targetType
  	 */
	@ApiModelProperty(value = "targetType")
  	private String targetType;
  	
  	/**
  	 * targetId
  	 */
	@ApiModelProperty(value = "targetId")
  	private String targetId;
  	
  	/**
  	 * targetName
  	 */
	@ApiModelProperty(value = "targetName")
  	private String targetName;
  	
  	/**
  	 * targetOrder
  	 */
	@ApiModelProperty(value = "targetOrder")
  	private Long targetOrder;
  	
  	/**
  	 * createDate
  	 */
	@ApiModelProperty(value = "createDate")
  	private java.util.Date createDate;
  	
  	
  	//属性 end
  	
  	public enum FieldNames{
        /** id */
        id("id","ID"),
        /** catId */
        catId("catId","CAT_ID"),
        /** targetType */
        targetType("targetType","TARGET_TYPE"),
        /** targetId */
        targetId("targetId","TARGET_ID"),
        /** targetName */
        targetName("targetName","TARGET_NAME"),
        /** targetOrder */
        targetOrder("targetOrder","TARGET_ORDER"),
        /** createDate */
        createDate("createDate","CREATE_DATE");

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
