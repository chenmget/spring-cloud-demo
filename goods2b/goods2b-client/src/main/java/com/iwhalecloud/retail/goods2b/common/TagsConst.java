package com.iwhalecloud.retail.goods2b.common;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年02月27日
 * @Description:
 **/
public class TagsConst {
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
