package com.iwhalecloud.retail.system.common;

/**
 * @author wenlong.zhong
 * @date 2019/6/18
 */
public class SysOrgConst {

    /**
     * 区域等级枚举
     */
    public enum ORG_LEVEL {
        LEVEL_0(0L, "中国电信"),
        LEVEL_1(1L, "湖南电信"),
        LEVEL_2(2L, "地市分公司"),
        LEVEL_3(3L, "区县分公司"),
        LEVEL_4(4L, "第四级组织"),
        LEVEL_5(5L, "第五级组织"),
        LEVEL_6(6L, "第六级：具体零售商级别");

        private Long code;
        private String value;

        ORG_LEVEL(Long code, String value) {
            this.code = code;
            this.value = value;
        }

        public Long getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

}
