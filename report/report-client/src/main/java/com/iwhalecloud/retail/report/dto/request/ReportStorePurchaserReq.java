package com.iwhalecloud.retail.report.dto.request;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class ReportStorePurchaserReq extends PageVO {

	  private static final long serialVersionUID = 1L;
	  private String brand;//品牌
	  private String city;//市县
	  private String createTimeEnd;//入库起止时间
	  private String createTimeStart;
	  private String businessEntityName;//经营主体名称
	  private String lanId;//地市
	  private String retailerCode;//零售商编号
	  private String retailerName;//零售商名称
	  private String outTimeEnd;//出库起止时间
	  private String outTimeStart;
	  private String productType;//机型
	  private String userType;//零售商标识
	  private String regionGrade; //地市标识
	  private String warningStatus;//库存预警状态

	  private String userId;
}
