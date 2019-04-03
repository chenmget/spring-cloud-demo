package com.iwhalecloud.retail.report.dto.request;

import java.io.Serializable;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class ReportDeSaleDaoReq extends PageVO {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
  private String brand;//品牌
  private String city;//市县
  private String createTimeEnd;//入库起止时间
  private String createTimeStart;
  private String gear;//档位
  private String lanId;//地市
  private String merchantCode;//地包商编号
  private String merchantName;//地包商名称
  private String outTimeEnd;//出库起止时间
  private String outTimeStart;
  private String productType;//机型
  private String warningStatus;//库存预警状态
  private String userType;//用户角色
  private String userId;
  private String typeId;//产品类型

}
