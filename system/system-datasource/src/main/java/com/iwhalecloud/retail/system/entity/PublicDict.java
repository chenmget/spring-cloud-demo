package com.iwhalecloud.retail.system.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * PublicDict
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("sys_public_dict")
@KeySequence(value="seq_sys_public_dict_type",clazz = String.class)
@ApiModel(value = "对应模型sys_public_dict, 对应实体PublicDict类")
public class PublicDict implements Serializable {
    /**表名常量*/
    public static final String TNAME = "sys_public_dict";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 模块数据    例如商品模块:goods    系统模块: system
  	 */
	@ApiModelProperty(value = "模块数据    例如商品模块:goods    系统模块: system")
  	private String module;
  	
  	/**
  	 * 数据类型     如 : 用户类型: user_type
  	 */
	@TableId
	@ApiModelProperty(value = "数据类型     如 : 用户类型: user_type")
  	private String type;
  	
  	/**
  	 * 键值
  	 */
	@ApiModelProperty(value = "键值")
  	private String pkey;
  	
  	/**
  	 * 键名
  	 */
	@ApiModelProperty(value = "键名")
  	private String pname;
  	
  	/**
  	 * 描述
  	 */
	@ApiModelProperty(value = "描述")
  	private String comments;
  	
  	/**
  	 * 扩展字段a
  	 */
	@ApiModelProperty(value = "扩展字段a")
  	private Integer codea;
  	
  	/**
  	 * 扩展字段
  	 */
	@ApiModelProperty(value = "扩展字段")
  	private String codeb;
  	
  	/**
  	 * 扩展字段
  	 */
	@ApiModelProperty(value = "扩展字段")
  	private String codec;
  	
  	/**
  	 * 扩展字段
  	 */
	@ApiModelProperty(value = "扩展字段")
  	private String coded;
  	
  	/**
  	 * sort
  	 */
	@ApiModelProperty(value = "排序字段")
  	private Integer sort;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 模块数据    例如商品模块:goods    系统模块: system. */
		module("module","MODULE"),
		
		/** 数据类型     如 : 用户类型: user_type. */
		type("type","TYPE"),
		
		/** 键值. */
		pkey("pkey","PKEY"),
		
		/** 键名. */
		pname("pname","PNAME"),
		
		/** 描述. */
		comments("comments","COMMENTS"),
		
		/** 扩展字段a. */
		codea("codea","CODEA"),
		
		/** 扩展字段. */
		codeb("codeb","CODEB"),
		
		/** 扩展字段. */
		codec("codec","CODEC"),
		
		/** 扩展字段. */
		coded("coded","CODED"),
		
		/** sort. */
		sort("sort","SORT");

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
