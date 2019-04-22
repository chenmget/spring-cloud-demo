package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import java.io.Serializable;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class PurApplyReq extends PageVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applyId;	//申请单号ID
	private String applyCode;	//申请单号
	private String projectName;//项目名称(申请单名称)
	private String applyTime;//申请时间
	private String startDate;
	private String endDate;
	private	String lanId;	//本地网
	private String userType;	//看是地市管理员还是省管理员
	private String applyMerchantId;//申请人名字
	private String applyType;//申请单类型，10 采购申请单、20 采购单
	private String statusCd;//当前处理环节
}
