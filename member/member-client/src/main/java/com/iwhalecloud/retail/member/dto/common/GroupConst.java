package com.iwhalecloud.retail.member.dto.common;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年03月05日
 * @Description:
 **/
public class GroupConst {

    /**
     * 群组状态
     */
    public enum GroupStatusEnum{
        /**
         * 无效
         */
        STATUS_0("0","无效"),
        /**
         * 有效
         */
        STATUS_1("1","有效");

        private String value;
        private String code;
        GroupStatusEnum(String code,String value){
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
     * 群组会员状态
     */
    public enum MemberGroupStatusEnum{
        /**
         * 无效
         */
        STATUS_0("0","无效"),
        /**
         * 有效
         */
        STATUS_1("1","有效");

        private String value;
        private String code;
        MemberGroupStatusEnum(String code,String value){
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
