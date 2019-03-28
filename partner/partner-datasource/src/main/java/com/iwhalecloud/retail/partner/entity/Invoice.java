package com.iwhalecloud.retail.partner.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("PAR_INVOICE")
@ApiModel(value = "对应模型PAR_INVOICE，对应实体ParInvoice类")
@KeySequence(value="seq_par_invoice_id",clazz = String.class)

public class Invoice implements Serializable {
    /**表名常量*/
    public static final String TNAME = "PAR_INVOICE";
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 发票信息ID
     */
    @TableId
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
    @ApiModelProperty(value = "发票类型：OTC-0006； 100普通发票 110普通增值税发票 120专用增值税发票 130电子发票 200收据")
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

    /** 字段名称枚举. */
    public enum FieldNames {
        /** 发票信息ID. */
        invoiceId("invoiceId","INVOICE_ID"),

        /** 商家ID. */
        merchantId("merchantId","MERCHANT_ID"),

        /** 商家名称. */
        merchantName("merchantName","MERCHANT_NAME"),

        /** 发票类型. */
        invoiceType("invoiceType","INVOICE_TYPE"),

        /** 纳税人识别号. */
        taxCode("taxCode","TAX_CODE"),

        /** 营业执照号. */
        busiLicenceCode("busiLicenceCode","BUSI_LICENCE_CODE"),

        /** 营业执照到期日期. */
        busiLicenceExpDate("busiLicenceExpDate","BUSI_LICENCE_EXP_DATE"),

        /** 公司地址. */
        registerAddress("registerAddress","REGISTER_ADDRESS"),

        /** 电话号码. */
        registerPhone("registerPhone","REGISTER_PHONE"),

        /** 开户银行. */
        registerBank("registerBank","REGISTER_BANK"),

        /** 银行账号. */
        registerBankAcct("registerBankAcct","REGISTER_BANK_ACCT"),

        /** 开户许可证  记录开户凭证附件地址. */
        acctCertificate("acctCertificate","ACCT_CERTIFICATE"),

        /** 营业执照附件  记录营业执照附件地址. */
        busiLicenceFile("busiLicenceFile","BUSI_LICENCE_FILE"),

        /** 专票状态  未录入/审核通过/审核中/审核不通过/已过期. */
        activityUrl("vatInvoiceStatus","VAT_INVOICE_STATUS");

        private String fieldName;
        private String tableFieldName;
        FieldNames(String fieldName, String tableFieldName){
            this.fieldName = fieldName;
            this.tableFieldName = tableFieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getTableFieldName() {
            return tableFieldName;
        }
    }

}
