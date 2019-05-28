package com.iwhalecloud.retail.system.dto;

import com.iwhalecloud.retail.system.common.SystemConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * CommonFile
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型sys_common_file, 对应实体CommonFile类")
public class CommonFileDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * fileId
  	 */
	@ApiModelProperty(value = "fileId")
  	private java.lang.String fileId;
	
	/**
  	 * 1：图片 2：文件
  	 */
	@ApiModelProperty(value = "1：图片 2：文件")
  	private java.lang.String fileType;
	
	/**
  	 * 1. 营业执照 2. 身份证照片 3. 授权证书4. 合同文本
  	 */
	@ApiModelProperty(value = "1. 营业执照 2. 身份证照片 3. 授权证书4. 合同文本")
  	private java.lang.String fileClass;
	
	/**
  	 * 附件归属的对象ID，如商家ID、用户ID等，查询时可以表示多个用,分割
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

	public CommonFileDTO(String fileType, String fileClass, String objId, String fileUrl) {
		this.fileType = fileType;
		this.fileClass = fileClass;
		this.objId = objId;
		this.fileUrl = fileUrl;
		this.createDate = new Date();
		this.createStaff = "1";
		this.statusCd = SystemConst.StatusCdEnum.STATUS_CD_VALD.getCode();
	}
}
