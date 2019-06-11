package com.iwhalecloud.retail.goods2b.common;

/**
 * @author mzl
 * @date 2018/12/24
 */
public class CatComplexConst {

    /**
     * 附件的目标类型
     */
    public enum TargetType {
        CAT_BRAND_TARGET("1", "分类关联品牌"),
        CAT_REL_TARGET("2", "分类关联产品"),
        CAT_RECOMMEND_TARGET("3", "分类推荐商品");

        private String type;
        private String name;

        TargetType(String type, String name) {
            this.type = type;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
