package com.iwhalecloud.retail.report.dto.request;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class ReportCodeStatementsReq extends PageVO  {
	
	private static final long serialVersionUID = 1L;
	private String serialToCity; // 串码流向区县
	private String serialToLanId; // 串码流入地市
	private String serialFrom; // 串码来源
	private String serialType; // 串码类型
	private String serialStatus; // 串码状态
    private String registerType; // 自注册状态
    private String brand; // 品牌
    private String productType; // 机型
    private String jyName; // 经营主体名称
    private String city; // 市县
    private String lanId; // 地市
    private String lssName; // 零售商名称
    private String lssCode; // 零售商编码
    private String gysName; // 供应商名称
    private String gysCode; // 供应商编码
    private String orderNo; // 订单号
    private String serial; // 串码
    private String fromLss; // 归属零售商
    private String outTimeStart; // 出库起时间
    private String outTimeEnd; // 出库止时间
    private String createTimeStart; // 入库起时间
    private String createTimeEnd; // 入库止时间
    private String xdCreateTimeStart; // 下单起时间
    private String xdCreateTimeEnd; // 下单止时间     
    private String userType;
    private String productCode; // 产品编码
    private String manufacturerCode;//厂家编码
    private String cpType; // 产品类别
    private String typeId;//产品类型

}
