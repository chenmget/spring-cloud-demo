package com.iwhalecloud.retail.report.dto.response;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

@Data
public class MktResInstResq extends PageVO {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String stockNum;//库存量
	private String stockAmount;//库存金额
	private String productId;//产品ID
	private String typeId;//产品类型
	private String productBaseName;//机型名称
	private String productName;//产品名称
	private String brandId;//品牌ID
	private String priceLevel;//机型档位
	private String brandName;//品牌名称
	private String productBaseId;//机型ID
	private String createDate;//创建时间
}
