package com.iwhalecloud.retail.goods.common;

/**
 * @author mzl
 * @date 2018/10/30
 */
public class GoodsConst {

    /**
     * 关联类型
     */
    public static final String RelType = "RECOMMEND";

    /**
     * 系统来源
     */
    public static final String SourceFrom = "MM";

    public static final Integer MARKET_ENABLE_ZERO = 0;

    public static final Integer MARKET_ENABLE = 1;

    //商品-权益 关联 状态 1000有效  1100无效
    public static final String GOODS_RIGHT_STATUS_INVALID = "1100";
    public static final String GOODS_RIGHT_STATUS_VALID = "1000";

    public static final String DEFULT_STORE = "0";

    public static final String DEFAULT_SPEC_PRICE = "0";

    /**
     * 状态常量
     */
    public enum GoodsType {
        TERMINAL("1","终端"),
        PACKAGE("2","套餐"),
        PARTS("3","配件"),
        CONTRACT("4","合约计划"),
        OTHER("5","其它");

        private String code;
        private String value;

        GoodsType(String code, String value){
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

    /**
     * 是否被删除
     */
    public enum GoodsRelType {
        /**
         * 商品关联推荐
         */
        RECOMMEND(0,"RECOMMEND"),
        /**
         * 关联终端
         */
        TERMINAL_PLAN(1,"TERMINAL_PLAN"),
        /**
         * 关联套餐
         */
        CONTRACT_OFFER(2,"CONTRACT_OFFER");
        private String value;
        private Integer code;
        GoodsRelType(Integer code, String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 是否可用
     */
    public enum DisabledEnum {
        /**
         * 可用
         */
        ENABLE(0,"可用"),
        /**
         * 不可用
         */
        DISABLE(1,"不可用");
        private String value;
        private Integer code;
        DisabledEnum(Integer code, String value){
            this.code = code;
            this.value = value;
        }

        public Integer getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }


    /**
     * 状态常量
     */
    public enum StatusCdEnum {
        STATUS_CD_VALD("1000","有效"),
        STATUS_CD_INVALD("1100","失效");

        private String code;
        private String value;

        StatusCdEnum(String code, String value){
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

    /**
     * 商品列表排序类型
     */
    public enum SortTypeEnum {

        /**
         * 商品列表排序规则
         */
        CREATE_TIME_DESC(1, "CREATE_TIME DESC"),

        CREATE_TIME_ASC(2, "CREATE_TIME ASC"),

        PRICE_DESC(3, "PRICE DESC"),

        PRICE_ASC(4, "PRICE ASC"),

        BUY_COUNT_DESC(5, "BUY_COUNT DESC"),

        BUY_COUNT_ASC(6, "BUY_COUNT ASC");


        private Integer code;

        private String value;

        SortTypeEnum(Integer code, String value) {
            this.code = code;
            this.value = value;
        }

        public Integer getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        /**
         * 根据key获取value
         *
         * @param key
         * @return String
         */
        public static String getValueByKey(Integer key) {
            SortTypeEnum[] enums = SortTypeEnum.values();
            for (int i = 0; i < enums.length; i++) {
                if (enums[i].getCode().equals(key)) {
                    return enums[i].getValue();
                }
            }
            return null;
        }
    }
}
