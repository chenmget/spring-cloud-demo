package com.iwhalecloud.retail.system.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * CommonRegion
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("sys_common_region")
@KeySequence(value="seq_sys_common_region_id",clazz = String.class)
@ApiModel(value = "本地区域对象，对应模型sys_common_region, 对应实体CommonRegion类")
public class CommonRegion implements Serializable {
    /**表名常量*/
    public static final String TNAME = "sys_common_region";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 公共管理区域标识
  	 */
	@TableId
	@ApiModelProperty(value = "公共管理区域标识")
  	private String regionId;
  	
  	/**
  	 * 记录上级区域标识。
  	 */
	@ApiModelProperty(value = "记录上级区域标识。")
  	private String parRegionId;
  	
  	/**
  	 * 记录区域名称。
  	 */
	@ApiModelProperty(value = "记录区域名称。")
  	private String regionName;
  	
  	/**
  	 * 记录区域编码。
  	 */
	@ApiModelProperty(value = "记录区域编码。")
  	private String regionNbr;
  	
  	/**
  	 * 记录区域类型。LOVB=LOC-0001
  	 */
	@ApiModelProperty(value = "记录区域类型。LOVB=LOC-0001")
  	private String regionType;
  	
  	/**
  	 * 记录区域描述。
  	 */
	@ApiModelProperty(value = "记录区域描述。")
  	private String regionDesc;
  	
  	/**
  	 * 记录区域的级别。LOVB=LOC-C-0004
  	 */
	@ApiModelProperty(value = "记录区域的级别。LOVB=LOC-C-0004")
  	private String regionLevel;
  	
  	/**
  	 * 区号
  	 */
	@ApiModelProperty(value = "区号")
  	private String areaCode;
  	
  	/**
  	 * 路径名称
  	 */
	@ApiModelProperty(value = "路径名称")
  	private String pathName;
  	
  	/**
  	 * 路径编码
  	 */
	@ApiModelProperty(value = "路径编码")
  	private String pathCode;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 公共管理区域标识. */
		regionId("regionId","REGION_ID"),
		
		/** 记录上级区域标识。. */
		parRegionId("parRegionId","PAR_REGION_ID"),
		
		/** 记录区域名称。. */
		regionName("regionName","REGION_NAME"),
		
		/** 记录区域编码。. */
		regionNbr("regionNbr","REGION_NBR"),
		
		/** 记录区域类型。LOVB=LOC-0001. */
		regionType("regionType","REGION_TYPE"),
		
		/** 记录区域描述。. */
		regionDesc("regionDesc","REGION_DESC"),
		
		/** 记录区域的级别。LOVB=LOC-C-0004. */
		regionLevel("regionLevel","REGION_LEVEL"),
		
		/** 区号. */
		areaCode("areaCode","AREA_CODE"),
		
		/** 路径名称. */
		pathName("pathName","PATH_NAME"),
		
		/** 路径编码. */
		pathCode("pathCode","PATH_CODE");

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
