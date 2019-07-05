package com.iwhalecloud.retail.order2b.dto.response.purapply;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class PurApplyStatusReportResp extends PageVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applyId;
	private String applyCode;		//申请单号
	private String applyName;		//项目名称
	private  String merchantId;//供应商ID
	private String merchantName;  //供应商名称
 	private  String lanId; // 申请地市
	private  String applyCity; // 申请地市
	private  String purType; // 采购类型
	private  String purNum; // 采购数量
	private  String statusCd; // 采购申请单状态
	//产品信息字段
	private  String productId; // 产品Id
	private  String productName; // 产品名称

	private  String corporationPrice;// 政企价格
	private  String sn;// 产品25位编码


	private String unitType;//产品型号
	private String unitTypeName; //型号名称

	private String brandName;// 品牌名称

	private String color; //颜色
	private  String memory;//内存

	private  String attrValue1;//容量-规格1 字段

	private  String attrValue2;//容量-规格1 字段

	private  String attrValue3;//容量-规格1 字段

	private  String attrValue4;//容量-规格1 字段

	private  String attrValue5;//容量-规格1 字段

	private  String attrValue6;//容量-规格1 字段

	private  String attrValue7;//容量-规格1 字段

	private  String attrValue8;//容量-规格1 字段

	private  String attrValue9;//容量-规格1 字段

	private  String attrValue10;//容量-规格1 字段












}
