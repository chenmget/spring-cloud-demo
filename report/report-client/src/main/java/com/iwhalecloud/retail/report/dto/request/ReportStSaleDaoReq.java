package com.iwhalecloud.retail.report.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

@Data
public class ReportStSaleDaoReq extends PageVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String createTimeStart;//入库起止时间
	private String createTimeEnd;//入库起止时间
	private String outTimeEnd;//出库起止时间
	private String outTimeStart;//出库起止时间
	private String brandName; 		//品牌
	private String productBaseName; 	//机型
	private String partnerName; 	//零售商名称
	private String partnerCode; 	//零售商编码
	private String businessEntityName; //经营主体名称
	private String typeName; 		//产品类型
	private String cityId; 		//地市
	private String countyId; 		//区县
	private String stockWarning; 	//库存预警
}
