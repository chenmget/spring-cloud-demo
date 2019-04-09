package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wenlong.zhong
 * @date 2019/3/11
 */

@Data
@ApiModel("发票信息列表 请求对象")
public class InvoiceListReq implements Serializable {

    @ApiModelProperty(value = "纳税人识别号（税号）")
    private String taxCode;

    @ApiModelProperty(value = "营业执照号")
    private String busiLicenceCode;

    @ApiModelProperty(value = "营业执照到期日期（只传这个会查出 到期日期  大于此日期的 数据）")
    private Date busiLicenceExpDate;

    @ApiModelProperty(value = "营业执照到期日期 开始时间 （跟结束时间一起 查 营业执照到期日期 在这个区间的")
    private Date startExpireDate;

    @ApiModelProperty(value = "营业执照到期日期 结束时间")
    private Date endExpireDate;

    @ApiModelProperty(value = "银行账号（公司账号）")
    private String registerBankAcct;


    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    @ApiModelProperty(value = "发票类型：OTC-0006； 100\t普通发票 110\t普通增值税发票 120\t专用增值税发票 130\t电子发票 200\t收据")
    private String invoiceType;

    @ApiModelProperty(value = "专票状态 1 未录入/2 审核通过/3 审核中/4 审核不通过/5 已过期")
    private String vatInvoiceStatus;
}
