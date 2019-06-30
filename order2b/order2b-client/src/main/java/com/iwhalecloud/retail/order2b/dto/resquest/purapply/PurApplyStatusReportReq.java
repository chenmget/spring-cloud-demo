package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PurApplyStatusReportReq extends PageVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applyId;	//申请单号ID
	private String applyCode;	//申请单号
	private String applyName;//项目名称(申请单名称)

	private String merchantName; // 供应商名称

    private String regionId;// 其实是lanId 因为使用lanId page aop 会拦截把他设置null



	// 产品字段
	private  String productName; // 产品名称


	private String unitType;//产品型号

	private  String sn; // 产品编码

	private String color; //颜色

	private  String memory;//内存

	private List<String> productIdList;//产品id 列表

	private List<String> merchantIdList;//供应商id 列表

	private	List<String> lanIdList;	//地市集合

	private	String statusCd;	//状态

	private  String purType;



}
