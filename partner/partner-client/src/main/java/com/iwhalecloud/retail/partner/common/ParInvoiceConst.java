package com.iwhalecloud.retail.partner.common;

/**
 * @author li.xinhang
 * @date 2019/03/04
 */
public class ParInvoiceConst {

    /**
     *  发票类型    100普通发票 110普通增值税发票 120专用增值税发票 130电子发票 200收据
     */
    public enum InvoiceType {
        NORMAL_INVOICE("100","普通发票"),
        NORMAL_VAT_INVOICE("110","普通增值税发票"),
        SPECIAL_VAT_INVOICE("120","专用增值税发票"),
        ELECTRONIC_INVOICE("130","电子发票"),
        RECEIPT("200","收据");
        private String value;
        private String code;
        InvoiceType(String code, String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     *  专票状态     1 未录入/2 审核通过/3 审核中/4 审核不通过/5 已过期
     */
    public enum VatInvoiceStatus {
        NOT_ENTERED("1","未录入"),
        AUDITED("2","审核通过"),
        AUDITING("3","审核中"),
        NOT_AUDITED("4","审核不通过"),
        EXPIRED("5","已过期");
        private String value;
        private String code;
        VatInvoiceStatus(String code, String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getCode() {
            return code;
        }
    }
    
}
