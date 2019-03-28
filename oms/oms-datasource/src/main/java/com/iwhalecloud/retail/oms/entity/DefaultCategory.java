package com.iwhalecloud.retail.oms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 默认运营位及内容
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("DEFAULT_CATEGORY")
@ApiModel(value = "对应模型DEFAULT_CATEGORY, 对应实体DefaultCategory类")
public class DefaultCategory implements Serializable {
    /**表名常量*/
    public static final String TNAME = "DEFAULT_CATEGORY";
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
	@ApiModelProperty(value = "修改人")
  	private java.lang.String modifier;
  	
  	/**
  	 * 是否删除：0未删、1删除	
  	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除	")
  	private java.lang.Long isDeleted;
  	
  	/**
  	 * 默认货架类目名称
  	 */
	@ApiModelProperty(value = "默认货架类目名称")
  	private java.lang.String categoryName;
  	
  	/**
  	 * 类目展示样式：推荐、九宫格、轮播
  	 */
	@ApiModelProperty(value = "类目展示样式：推荐、九宫格、轮播")
  	private java.lang.String categoryStyle;
  	
  	/**
  	 * 关联的货架模板编号
  	 */
	@ApiModelProperty(value = "关联的货架模板编号")
  	private java.lang.String goodsCategoryId;
  	
  	/**
  	 * 对应的商品类别名称
  	 */
	@ApiModelProperty(value = "对应的商品类别名称")
  	private java.lang.String goodsCategoryName;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** ID. */
		id("id","ID"),
		
		/** 创建时间. */
		gmtCreate("gmtCreate","GMT_CREATE"),
		
		/** 修改时间. */
		gmtModified("gmtModified","GMT_MODIFIED"),
		
		/** 创建人. */
		creator("creator","CREATOR"),
		
		/** 修改人. */
		modifier("modifier","MODIFIER"),
		
		/** 是否删除：0未删、1删除	. */
		isDeleted("isDeleted","IS_DELETED"),
		
		/** 默认货架类目名称. */
		categoryName("categoryName","CATEGORY_NAME"),
		
		/** 类目展示样式：推荐、九宫格、轮播. */
		categoryStyle("categoryStyle","CATEGORY_STYLE"),
		
		/** 关联的货架模板编号. */
		goodsCategoryId("goodsCategoryId","GOODS_CATEGORY_ID"),
		
		/** 对应的商品类别名称. */
		goodsCategoryName("goodsCategoryName","GOODS_CATEGORY_NAME");

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
