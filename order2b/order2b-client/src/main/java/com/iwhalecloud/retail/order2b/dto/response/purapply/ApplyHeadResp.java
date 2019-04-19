package com.iwhalecloud.retail.order2b.dto.response.purapply;

import java.io.Serializable;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class ApplyHeadResp extends PageVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applyId;	//申请单ID
	private String applyCode;//申请单号
	private String applyAdress;//申请地市
	private String applyDepartment;//申请部门
	private String applyMerchantCode;//申请人
	
	private String relCode;
	private String lanId;
	private String regionId;
	

}
