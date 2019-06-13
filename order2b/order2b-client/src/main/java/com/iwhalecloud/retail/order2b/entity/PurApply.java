package com.iwhalecloud.retail.order2b.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "对应模型purApply, 对应实体PurApply类")
@TableName("PUR_APPLY")
public class PurApply implements Serializable {/**
	 * 
	 */
	public static final String TNAME = "pur_apply";
	private static final long serialVersionUID = 1L;
	
	/**
	 * 申请单id
	 */
	@ApiModelProperty(value = "applyId")
	@TableId
    private java.lang.String applyId;
	
	/**
	 * 申请单编码
	 */
	@ApiModelProperty(value = "applyCode")
    private java.lang.String applyCode;
	
	/**
	 * 申请单名称
	 */
	@ApiModelProperty(value = "applyName")
    private java.lang.String applyName;
	
	/**
	 * 申请单类型
	 */
	@ApiModelProperty(value = "applyType")
    private java.lang.String applyType;
	
	/**
	 * 申请单描述
	 */
	@ApiModelProperty(value = "content")
    private java.lang.String content;
	
	/**
	 * 供应商id
	 */
	@ApiModelProperty(value = "merchantId")
    private java.lang.String merchantId;
	
	/**
	 * 供应商编码
	 */
	@ApiModelProperty(value = "merchantCode")
    private java.lang.String merchantCode;
	
	/**
	 * 申请商家id
	 */
	@ApiModelProperty(value = "applyMerchantId")
    private java.lang.String applyMerchantId;
	
	/**
	 * 申请商家编码
	 */
	@ApiModelProperty(value = "applyMerchantCode")
    private java.lang.String applyMerchantCode;
	
	/**
	 * 申请人联系电话
	 */
	@ApiModelProperty(value = "phone")
    private java.lang.String phone;
	
	/**
	 * 本地网标识
	 */
	@ApiModelProperty(value = "lanId")
    private java.lang.String lanId;
	
	/**
	 * 区域标识
	 */
	@ApiModelProperty(value = "regionId")
    private java.lang.String regionId;
	
	/**
	 * 关联单号
	 */
	@ApiModelProperty(value = "relApplyId")
    private java.lang.String relApplyId;
	
	/**
	 * 状态
	 */
	@ApiModelProperty(value = "statusCd")
    private java.lang.String statusCd;
	
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "createStaff")
    private java.lang.String createStaff;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "createDate")
    private java.lang.String createDate;
	
	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "updateStaff")
    private java.lang.String updateStaff;
	
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "updateDate")
    private java.lang.String updateDate;
	
	
	
}
