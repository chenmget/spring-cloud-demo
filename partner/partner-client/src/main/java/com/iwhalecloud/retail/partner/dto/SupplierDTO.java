package com.iwhalecloud.retail.partner.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 供应商
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型PAR_SUPPLIER, 对应实体Supplier类")
public class SupplierDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "员工账户 SYS_USER表的 login_name字段̨")
	private java.lang.String loginName;

	//属性 begin
	/**
	 * 供应商ID
	 */
	@ApiModelProperty(value = "供应商ID")
	private java.lang.String supplierId;

	/**
	 * 供应商编码
	 */
	@ApiModelProperty(value = "供应商编码")
	private java.lang.String supplierCode;

	/**
	 * 供应商名称
	 */
	@ApiModelProperty(value = "供应商名称")
	private java.lang.String supplierName;

	/**
	 * 联系人
	 */
	@ApiModelProperty(value = "联系人")
	private java.lang.String linkMan;

	/**
	 * 联系人电话
	 */
	@ApiModelProperty(value = "联系人电话")
	private java.lang.String phoneNo;

	/**
	 * 供应商类型:1省包、2地包
	 */
	@ApiModelProperty(value = "供应商类型:1省包、2地包")
	private java.lang.String supplierType;

	/**
	 * 状态: 1有效、0失效
	 */
	@ApiModelProperty(value = "状态: 1有效、0失效")
	private java.lang.String supplierState;

	/**
	 * 员工id
	 */
	@ApiModelProperty(value = "员工id")
	private java.lang.String userId;

	/**
	 * DELETE_FLAG
	 */
	@ApiModelProperty(value = "DELETEFLAG")
	private java.lang.Long deleteFlag;

}
