package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * MerchantTagRel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("prod_merchant_tag_rel")
@KeySequence(value="seq_prod_merchant_tag_rel_id",clazz = String.class)
@ApiModel(value = "对应模型prod_merchant_tag_rel, 对应实体MerchantTagRel类")
public class MerchantTagRel implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_partner_tag_rel";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 关联ID
  	 */
	@TableId
	@ApiModelProperty(value = "关联ID")
  	private String relId;
  	
  	/**
  	 * 标签ID
  	 */
	@ApiModelProperty(value = "标签ID")
  	private String tagId;
  	
  	/**
  	 * 商家 ID
  	 */
	@ApiModelProperty(value = "商家 ID")
  	private String merchantId;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 关联ID. */
		relId("relId","REL_ID"),
		
		/** 标签ID. */
		tagId("tagId","TAG_ID"),
		
		/** 商家 ID. */
		merchantId("merchantId","MERCHANT_ID");

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
