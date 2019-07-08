package com.iwhalecloud.retail.goods2b.common;

/**
 * @Author: he.sw
 * @Date: 2019年06月01日
 * @Description:
 **/
public class TypeConst {

    /**
     * 产品类型细类
     */
    public enum TYPE_DETAIL {

        MOBLIE("0","手机"),

        ROUTER("1","路由器"),

        INTELLIGENT_TERMINA("2","泛智能终端"),

        FUSION_TERMINAL("3","融合终端"),

        SET_TOP_BOX("4","机顶盒"),

        OPTICAL_MODEM("5","光猫");

        private String code;
        private String value;

        TYPE_DETAIL(String code, String value){
            this.code = code;
            this.value = value;
        }
        public String getCode() {
            return code;
        }
        public String getValue() {
            return value;
        }

        public static String getValueByCode(String code) {
            TYPE_DETAIL[] enums = values();
            for (TYPE_DETAIL obj : enums) {
                if (obj.code.equals(code)) {
                    return obj.value;
                }
            }
            return null;
        }
    }

}
