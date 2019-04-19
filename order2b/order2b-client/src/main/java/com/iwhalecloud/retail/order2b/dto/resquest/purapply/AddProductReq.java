package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import java.io.Serializable;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class AddProductReq extends PageVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productName;		//产品名称(产品ID)
	private String brandName;	//品牌
	private String unitType;	//产品型号
	private String specName;	//规格
	private String color;	//颜色
	private String cost;	//零售价
	private String snCount;	//数量(采购数量)
	private String priceInStore;  //供货价(采购价格)
	private String purchaseType;  //采购类型
	
	private String applyItemId;//	申请单项ID
	private String applyId;//申请单ID
	private String statusCd;//状态
	private String createStaff;	//创建人
	private String createDate;	//创建时间
	private String updateStaff;	//修改人
	private String updateDate;	//修改时间
	private String statusDate;  //状态时间
	
	
}
