package com.iwhalecloud.retail.member.common;

public class MemberConst {
    /**
     *  通用状态 1 有效、0失效
     */
    public enum CommonState {
        VALID("1","有效"),
        INVALID("0","无效");
        private String value;
        private String code;
        CommonState(String code, String value){
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
     *  是否默认地址 1是、0否
     */
    public enum IsDefaultAddress {
        YES("1","是默认"),
        NO("0","非默认");
        private String value;
        private String code;
        IsDefaultAddress(String code, String value){
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
     * 会员等级
     */
    public enum LvTypeEnum{
        /**
         * 普通会员
         */
        LV_0("0","普通会员"),
        /**
         * 银VIP会员
         */
        LV_1("1","银VIP会员"),
        /**
         * 金VIP会员
         */
        LV_2("2","金VIP会员");

        private String value;
        private String code;
        LvTypeEnum(String code,String value){
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
     *  是否默认等级 1是、0否
     */
    public enum IsDefaultLevel {
        /**
         * 是
         */
        YES("1","是"),
        /**
         * 否
         */
        NO("0","否");

        private String value;
        private String code;

        IsDefaultLevel(String code, String value){
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
