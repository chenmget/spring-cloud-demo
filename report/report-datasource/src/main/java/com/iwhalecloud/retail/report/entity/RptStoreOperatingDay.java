package com.iwhalecloud.retail.report.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("rpt_partner_operating_day")
@KeySequence(value="seq_rpt_partner_operating_day_id",clazz = String.class)
@ApiModel(value = "rpt_partner_operating_day, 对应实体RptPartner类")
public class RptStoreOperatingDay implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId
	private String itemId;
	private String date;
	private String partnerId;
	private String partnerCode;//零售商编码
	private String partnerName;//零售商名称
	private String businessEntityId;
	private String businessEntityCode;
	private String businessEntityName;//所属经营主体
	private String cityId;//所属城市
	private String countryId;//所属区县
	private String goodsId;
	private String productId;
	private String brandId;
	private String sellNum;
	private String contractNum;
	private String writeoffNum;
	private String registerNum;
	private String sellAmount;
	private String purchaseAmount;
	private String purchaseNum;
	private String manualNum;
	private String transInNum;
	private String transOutNum;
	private String returnNum;
	private String totalInNum;
	private String totalOutNum;
	private String stockNum;
	private String stockAmount;
	private String createDate;
}
