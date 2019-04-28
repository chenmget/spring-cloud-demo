package com.iwhalecloud.retail.order2b.dto.response.purapply;

import java.io.Serializable;
import java.util.List;

import com.iwhalecloud.retail.dto.PageVO;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.AddFileReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.AddProductReq;

import lombok.Data;

@Data
public class CkProcureApplyResp extends PageVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String isSave;//如果是1就是保存，2就是提交
	private String applyId;	//	申请单ID
	private String applyCode;	//单号申请
	private String applyName;	//..
	private String applyMerchantCode;	//申请人
	private String applyAddress;	//申请地市
	private String applyDepartment;	//申请部门
	private String applyContact;	//联系方式
	private String supplierName;	//供应商名称
	private String applyType;//	申请单类型，10 采购申请单、20 采购单
	private String content;//申请单描述
	private String merchantCode;//供应商编码
	private String applyMerchantId;//申请人ID
	private String lanId;//本地网
	private String regionId;//区域标识
	private String relApplyId;//关联单号
	private String statusCd;//状态
	private String statusDate;//状态时间
	private String createStaff;	//创建人
	private String createDate;	//创建时间
	private String updateStaff;	//修改人
	private String updateDate;	//修改时间
	private String applyMerchantName;//申请人名称
	private String supplierCode;//供应商编码
	private String supplierId;//供应商ID
	private List<AddProductReq> addProductReq;
	
	private List<AddFileReq> addFileReq;
}
