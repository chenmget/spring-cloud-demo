package com.iwhalecloud.retail.partner.dto;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * BusinessEntity
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型PAR_INVOICE，对应实体ParInvoice类")
public class InvoiceDTO extends AbstractRequest implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;


	//属性 begin
	/**
	 * 发票信息ID
	 */
	@ApiModelProperty(value = "发票信息ID")
	private String invoiceId;

	/**
	 * 商家ID
	 */
	@ApiModelProperty(value = "商家ID")
	private String merchantId;

	/**
	 * 商家名称
	 */
	@ApiModelProperty(value = "商家名称")
	private String merchantName;

	/**
	 * 发票类型
	 */
	@ApiModelProperty(value = "发票类型：OTC-0006； 100\t普通发票 110\t普通增值税发票 120\t专用增值税发票 130\t电子发票 200\t收据")
	private String invoiceType;

	/**
	 * 纳税人识别号
	 */
	@ApiModelProperty(value = "纳税人识别号")
	private String taxCode;

	/**
	 * 营业执照号
	 */
	@ApiModelProperty(value = "营业执照号")
	private String busiLicenceCode;

	/**
	 * 营业执照到期日期
	 */
	@ApiModelProperty(value = "营业执照到期日期")
	private Date busiLicenceExpDate;

	/**
	 * 公司地址
	 */
	@ApiModelProperty(value = "公司地址")
	private String registerAddress;

	/**
	 * 电话号码
	 */
	@ApiModelProperty(value = "电话号码")
	private String registerPhone;

	/**
	 * 开户银行
	 */
	@ApiModelProperty(value = "开户银行")
	private String registerBank;

	/**
	 * 银行账号
	 */
	@ApiModelProperty(value = "银行账号")
	private String registerBankAcct;

	/**
	 * 开户许可证
	 */
	@ApiModelProperty(value = "开户许可证   记录开户凭证附件地址")
	private String acctCertificate;

	/**
	 * 营业执照附件
	 */
	@ApiModelProperty(value = "营业执照附件   记录营业执照附件地址")
	private String busiLicenceFile;

	/**
	 * 专票状态
	 */
	@ApiModelProperty(value = "专票状态   1 未录入/2 审核通过/3 审核中/4 审核不通过/5 已过期")
	private String vatInvoiceStatus;


	//属性 end
	
  	
}
