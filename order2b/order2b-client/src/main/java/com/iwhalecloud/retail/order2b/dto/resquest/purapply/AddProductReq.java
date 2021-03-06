package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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
	private String sn;//产品编码
	private String applyItemId;//	申请单项ID
	private String applyId;//申请单ID
	private String statusCd;//状态
	private String createStaff;	//创建人
	private String createDate;	//创建时间
	private String updateStaff;	//修改人
	private String updateDate;	//修改时间
	private String statusDate;  //状态时间
	private String isFixedLine;//是否固网产品
	private String typeName;//产品类型
	private String productId;//产品ID
	private String attrValue2;//颜色
	private String attrValue3;//内存

	private String deliverCount;	//已发货数量
	private String parentTypeId;	//产品采购类型

	private List<String> deliverMktResInstNbrList; // 发货串码列表

	private String corporationPrice; //政企价格


	private String supplyFeeLower;//政企上限价


}
