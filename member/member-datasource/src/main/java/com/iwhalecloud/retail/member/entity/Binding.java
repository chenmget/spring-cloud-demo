package com.iwhalecloud.retail.member.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Member
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("MEM_BINDING")
@KeySequence(value = "seq_mem_binding_id",clazz = String.class)
@ApiModel(value = "对应模型MEM_BINDING, 对应实体Binding类")
public class Binding implements Serializable {
    /**表名常量*/
    public static final String TNAME = "MEM_BINDING";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * id
  	 */
	@TableId
	@ApiModelProperty(value = "id")
  	private java.lang.String id;
  	
	/**
	 * memberId
	 */
	@ApiModelProperty(value = "memberId")
	private java.lang.String memberId;
	
  	/**
  	 * targetType
  	 */
	@ApiModelProperty(value = "targetType")
  	private java.lang.Integer targetType;
  	
  	/**
  	 * targetId
  	 */
	@ApiModelProperty(value = "targetId")
  	private java.lang.String targetId;
  	
  	/**
  	 * uname
  	 */
	@ApiModelProperty(value = "uname")
  	private java.lang.String uname;
  	
  	//属性 end
	

	/** 字段名称枚举. */
    public enum FieldNames {
		/** 标识. */
    	id("id","id"),
		
		/** 会员ID. */
    	memberId("memberId","MEMBER_ID"),
		
		/** 第三方类型: 1:微信; 2:支付宝; 3: QQ  .... */
    	targetType("targetType","TARGET_TYPE"),
		
		/** 第三方平台的账号特地ID(如微信的openid). */
    	targetId("targetId","TARGET_ID"),
		
		/** 会员账号. */
    	uname("uname","uname");


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
