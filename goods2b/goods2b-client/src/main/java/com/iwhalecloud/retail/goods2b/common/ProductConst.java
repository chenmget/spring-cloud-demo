package com.iwhalecloud.retail.goods2b.common;

/**
 * @author mzl
 * @date 2018/10/30
 */
public class ProductConst {

    public static final String APP_PRODUCT_FLOW_PROCESS_ID="1";

    public static final String UPDATE_PRODUCT_FLOW_PROCESS_ID="10";

    /**
     * 状态常量
     */
    public enum StatusType {
        SUBMIT("1","待提交"),
        AUDIT("2","审核中"),
        EFFECTIVE("3","已挂网"),
        INEFFECTIVE("4","已退市");

        private String code;
        private String value;

        StatusType(String code, String value){
            this.code = code;
            this.value = value;
        }
        public static StatusType  getStatusTypeByCode(String code){
            StatusType[] values = StatusType.values();
            for (int i = 0; i <values.length ; i++) {
                if(code.equals(values[i].getCode())){
                    return values[i];
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }
        public String getValue() {
            return value;
        }
    }
    public enum AuditStateType {
        UN_SUBMIT("1","待提交"),
        AUDITING("2","审核中"),
        AUDIT_PASS("3","审核通过"),
        AUDIT_UN_PASS("4","审核不通过");



        private String code;
        private String value;

        AuditStateType(String code, String value){
            this.code = code;
            this.value = value;
        }
        public static AuditStateType getAuditStateTypeByCode(String code){
            AuditStateType[] values = AuditStateType.values();
            for (int i = 0; i <values.length ; i++) {
                if(code.equals(values[i].getCode())){
                    return values[i];
                }
            }
            return UN_SUBMIT;
        }

        public String getCode() {
            return code;
        }
        public String getValue() {
            return value;
        }
    }

    /**
     * 是否被删除
     */
    public enum IsDelete {
        /**
         * 否
         */
        NO("0","否"),
        /**
         * 是
         */
        YES("1","是");
        private String value;
        private String code;
        IsDelete(String code, String value){
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }
        public String getValue() {
            return value;
        }
    }

}
