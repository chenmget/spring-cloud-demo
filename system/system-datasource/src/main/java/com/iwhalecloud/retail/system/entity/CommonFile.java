package com.iwhalecloud.retail.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * CommonFile
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("sys_common_file")
@ApiModel(value = "对应模型sys_common_file, 对应实体CommonFile类")
public class CommonFile implements Serializable {
    /**表名常量*/
    public static final String TNAME = "sys_common_file";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * fileId
  	 */
	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "fileId")
  	private java.lang.String fileId;
  	
  	/**
  	 * 1：图片  2：文件
  	 */
	@ApiModelProperty(value = "1：图片  2：文件")
  	private java.lang.String fileType;
  	
  	/**
  	 * 1. 营业执照 2. 身份证照片 3. 授权证书4. 合同文本
  	 */
	@ApiModelProperty(value = "1. 营业执照 2. 身份证照片 3. 授权证书4. 合同文本")
  	private java.lang.String fileClass;
  	
  	/**
  	 * 附件归属的对象ID，如商家ID、用户ID等
  	 */
	@ApiModelProperty(value = "附件归属的对象ID，如商家ID、用户ID等")
  	private java.lang.String objId;
  	
  	/**
  	 * 附件路径
  	 */
	@ApiModelProperty(value = "附件路径")
  	private java.lang.String fileUrl;
  	
  	/**
  	 * 记录状态。LOVB=PUB-C-0001。
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
  	private java.lang.String statusCd;
  	
  	/**
  	 * 记录首次创建的用户标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的用户标识。")
  	private java.util.Date statusDate;
  	
  	/**
  	 * 记录首次创建的时间。
  	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
  	private java.lang.String createStaff;
  	
  	/**
  	 * 记录首次创建的时间。
  	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
  	private java.util.Date createDate;
  	
  	/**
  	 * 记录每次修改的员工标识。
  	 */
	@ApiModelProperty(value = "记录每次修改的员工标识。")
  	private java.lang.String updateStaff;
  	
  	/**
  	 * 记录每次修改的时间。
  	 */
	@ApiModelProperty(value = "记录每次修改的时间。")
  	private java.util.Date updateDate;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** fileId. */
		fileId("fileId","FILE_ID"),
		
		/** 1：图片  2：文件. */
		fileType("fileType","FILE_TYPE"),
		
		/** 1. 营业执照 2. 身份证照片 3. 授权证书4. 合同文本. */
		fileClass("fileClass","FILE_CLASS"),
		
		/** 附件归属的对象ID，如商家ID、用户ID等. */
		objId("objId","OBJ_ID"),
		
		/** 附件路径. */
		fileUrl("fileUrl","FILE_URL"),
		
		/** 记录状态。LOVB=PUB-C-0001。. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 记录首次创建的用户标识。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录首次创建的时间。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录首次创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录每次修改的员工标识。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录每次修改的时间。. */
		updateDate("updateDate","UPDATE_DATE");

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
