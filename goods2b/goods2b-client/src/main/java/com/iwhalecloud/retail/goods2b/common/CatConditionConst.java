package com.iwhalecloud.retail.goods2b.common;

/**
 * @author wenlong.zhong
 * @date 2019/6/4
 *
 * 商品类型条件 相关常量
 */
public class CatConditionConst {

    /**
     * 关联常量
     * 商品类型关联的筛选条件类型 1. 产品类型 2. 产品属性 3. 品牌 4. 营销活动类型 5. 产品标签
     */
    public enum RelType {
        PRODUCT_TYPE("1", "产品类型"),
        PRODUCT_ATTR("2", "产品属性"),
        BRAND("3", "品牌"),
        ACTIVITY_TYPE("4", "营销活动类型"),
        PRODUCT_TAG("5", "产品标签");

        private String code;
        private String value;

        RelType(String code, String value) {
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
