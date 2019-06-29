package com.iwhalecloud.retail.report.dto.request;

import java.util.List;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

@Data
public class ReportStorePurchaserReq extends PageVO {

	  private static final long serialVersionUID = 1L;
	  private String dateStart;//统计起始时间
	  private String dateEnd;//统计结束时间
	  private List<String> lanIdList; 		//地市
	  private String productName;   //产品名称
	  private String productType; 		//产品类型
	  private String brandName; 		//品牌
	  private String productBaseName; //产品型号

}