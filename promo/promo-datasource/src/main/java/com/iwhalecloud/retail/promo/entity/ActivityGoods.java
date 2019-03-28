package com.iwhalecloud.retail.promo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * ActivityGoods
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("act_activity_goods")
@KeySequence(value = "seq_act_activity_goods_id", clazz = String.class)
@ApiModel(value = "对应模型act_activity_goods, 对应实体ActivityGoods类")
public class ActivityGoods implements Serializable {
    /**表名常量*/
    public static final String TNAME = "act_activity_goods";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ID
  	 */
	@TableId
	@ApiModelProperty(value = "ID")
  	private java.lang.String id;
  	
  	/**
  	 * 营销活动code
  	 */
	@ApiModelProperty(value = "营销活动code")
  	private java.lang.String marketingActivityCode;
  	
  	/**
  	 * 商品id
  	 */
	@ApiModelProperty(value = "商品id")
  	private java.lang.String goodsId;
  	
  	/**
  	 * 商品名称
  	 */
	@ApiModelProperty(value = "商品名称")
  	private java.lang.String goodsName;
  	
  	/**
  	 * 是否自动上下架：0否、1是
  	 */
	@ApiModelProperty(value = "是否自动上下架：0否、1是")
  	private java.lang.Byte isAutoMarket;

	@ApiModelProperty(value = "创建人。")
	private java.lang.String creator;

	/**
	 * 记录首次创建的时间。
	 */
	@ApiModelProperty(value = "创建时间。")
	private java.util.Date gmtCreate;
	/**
	 * 记录每次修改的员工标识。
	 */
	@ApiModelProperty(value = "修改人。")
	private java.lang.String modifier;

	/**
	 * 记录每次修改的时间。
	 */
	@ApiModelProperty(value = "修改时间。")
	private java.util.Date gmtModified;
	/**
	 * 是否删除：0未删、1删除。
	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除。")
	private String isDeleted;
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** ID. */
		id("id","ID"),
		
		/** 营销活动code. */
		marketingActivityCode("marketingActivityCode","MARKETING_ACTIVITY_CODE"),
		
		/** 商品id. */
		goodsId("goodsId","GOODS_ID"),
		
		/** 商品名称. */
		goodsName("goodsName","GOODS_NAME"),
		
		/** 是否自动上下架：0否、1是. */
		isAutoMarket("isAutoMarket","IS_AUTO_MARKET"),

		/** 是否删除：0未删、1删除。. */
		isDeleted("isDeleted","IS_DELETED"),
		
		/** 记录首次创建的员工标识。. */
		creator("creator","CREATOR"),
		
		/** 记录首次创建的时间。. */
		gmtCreate("gmtCreate","GMT_CREATE"),
		
		/** 记录每次修改的员工标识。. */
		modifier("modifier","MODIFIER"),
		
		/** 记录每次修改的时间。. */
		gmtModified("gmtModified","GMT_MODIFIED");

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
