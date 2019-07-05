package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PurApplyReportReq extends PageVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applyId;	//申请单号ID
	private String applyCode;	//申请单号
	private String applyName;//项目名称(申请单名称)
	private	String lanId;	//申请地市
	private	String applyCity;	//申请地市名称
	private String purType;	//采购类型
	private String merchantName; // 供应商名称
	private String mktResInstNbr;//串码

	private  String merchantId;//供应商ID
    private String regionId;// 其实是lanId 因为使用lanId page aop 会拦截把他设置null

	private String purStartDate; //采购开始时间
	private String purEndDate; //采购结束时间

	private String deliveryStartDate; //发货开始时间deliveryDate
	private String deliveryEndDate; //发货结束时间

	private String revingStartDate; //收货开始时间
	private String revingEndDate; //收货结束时间

	// 产品字段
	private  String productName; // 产品名称


	private  String sn;// 产品25位编码


	private String unitType;//产品型号


	private String color; //颜色

	private  String memory;//内存

	private List<String> productIdList;//产品id 列表

	private List<String> merchantIdList;//供应商id 列表

	private	List<String> lanIdList;	//地市集合




}
