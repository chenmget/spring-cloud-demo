package com.iwhalecloud.retail.report.dto.request;

import java.util.List;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

@Data
public class ReportStSaleDaoReq extends PageVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String dateStart;//统计起始时间
	private String dateEnd;//统计结束时间
	private List<String> cityId; 		//地市
	private List<String> orgName; 		//经营单元
	private String stockWarning; 	//库存预警
	private String productType; 		//产品类型
	private String brandName; 		//品牌
	private String productName;   //产品名称
	private String productBaseName; //产品型号
	private String partnerName; 	//零售商名称
	private String partnerCode; 	//零售商编码
	private String businessEntityName; //经营主体名称
	
	
	
}
