package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import java.util.Date;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class ProdProductChangeReq extends PageVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String changeId;//	变更流水
	private String verNum;//	版本号
	private String productBaseId;//产品基本信息ID
	private String auditState;//审核状态： 1 待提交 2 审核中 3 审核通过 4 审核不通过
	private Date createDate;//	创建时间
	private String createStaff;//	创建人
	private String batchId;//批次号
}
