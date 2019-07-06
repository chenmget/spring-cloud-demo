package com.iwhalecloud.retail.report.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RptPartnerOperatingDay implements Serializable {
	
	  private static final long serialVersionUID = 1L;
	  private String lanIdName;           //地市
	    private String orgName;             //经营单元
	    private String totalInNum;          //入库总量
	    private String totalOutNum;         //出库总量
	    private String stockNum;      //库存量
	    private String purchaseNum;         //交易入库量
	    private String manualNum;           //手工入库量
	    private String transInNum;      //调拨入库量
	    private String sellNum;             //总销售量
	    private String uncontractNum;       //手工核销
	    private String contractNum;         //CRM销量
	    private String registerNum;         //自注册激活量
	    private String transOutNum;         //调拨出库量
	    private String returnNum;           //退库量
	    private String weekAvgSellNum;      //近7天日均销量
}
