package com.iwhalecloud.retail.report.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RptResInstDetailDay implements Serializable  {
	/**
	 * 
	*/
	private static final long serialVersionUID = 1L;

	private String mkt_res_inst_id;//串码实例标识(  营销资源实例的标识，主键)
	private String mkt_res_inst_nbr;//串码实例编码(  记录营销资源实例编码)
	private String date;//统计日期
	private String mkt_res_store_id;//仓库标识(  营销资源仓库标识)
	private String mkt_res_inst_type; //串码类型(  01 交易 02 备机 03 集采 )
	private String source_type;// 实例来源(  记录串码来源， 01 厂商 02 供应商 03 零售商)
	private String storage_type;//入库类型(入库类型：交易入库、调拨入库、领用入库、绿色通道  )
	private String city_id; //地市
	private String county_id;//区县
	private String product_type;//产品类型
	private String product_base_id; //型号id
	private String product_base_name; //型号名称
	private String product_id; //产品id
	private String product_name; //产品名称
	private String product_code;//产品编码（  集团25位编码）
	private String brand_id;//品牌id
	private String brand_name;//品牌名称
	private String order_id; //订单编号（交易入库的串码记录订单号）
	private String create_time;//下单时间
	private String receive_time;//入库时间
	private String out_time;//出库时间
	private String stock_age;//库龄
	private String manufacturer_code;//厂商编码（记录供应商编码）
	private String manufacturer_name;//厂商名称（记录供应商名称）
	private String supplier_code;//供应商编码（记录供应商编码）
	private String supplier_name;//供应商名称（记录供应商名称）
	private String partner_id;//零售商id
	private String partner_code;//零售商编码
	private String partner_name;//零售商名称
	private String business_entity_id;//经营主体id
	private String business_entity_code;//经营主体编码
	private String business_entity_name;//经营主体名称
	private String dest_city_id;//流向地市
	private String dest_county_id;//流向区县
	private String dest_merchant_id;//串码流向
	private String self_reg_status;//自注册状态（记录自注册状态）
	private String status_cd;//状态
	private String status_date;//状态时间（记录状态变更的时间）
	
}
