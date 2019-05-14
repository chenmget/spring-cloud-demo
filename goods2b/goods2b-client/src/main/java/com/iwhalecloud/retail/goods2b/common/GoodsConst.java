package com.iwhalecloud.retail.goods2b.common;

/**
 * @author mzl
 * @date 2018/10/30
 */
public class GoodsConst {

    // 表对应的缓存名称，1、表名的缓存名称：表示单条的缓存的；2、非表名的缓存: 表示存非单条的数据（一般是有父子级关系的列表）
    // 文件上传表prod_file使用的缓存名称
    public static final String CACHE_NAME_PROD_FILE = "prod_file";

    public static final String CACHE_NAME_GOODS_SALE_ORDER = "goods_sale_order";

    public static final String CACHE_KEY_GOODS_SALE_ORDER_7 = "goods_sale_order_7";

    public static final String CACHE_KEY_GOODS_SALE_ORDER_30 = "goods_sale_order_30";
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
     * 逻辑 删除
     */
    public static final Integer DELETE = 1;
    /**
     * 逻辑 不删除
     */
    public static final Integer NO_DELETE = 0;

    /**
     * 有库存
     */
    public static final Integer HAVE_STOCK = 1;

    /**
     * 没有库存
     */
    public static final Integer NOT_HAVE_STOCK = 0;

    /**
     * 逻辑 优惠卷有效
     */
    public static final String EFFECTIVE = "1";

    /**
     * 逻辑 优惠卷无效
     */
    public static final String NO_EFFECTIVE = "0";

    /**
     * 分类根目录
     */
    public static final String ROOT_DIR = "-1";

    /**
     * 商品发布对象 按区域
     */
    public static final String TARGET_TYPE_REGION = "1";
    /**
     * 商品发布对象 按对象
     */
    public static final String TARGET_TYPE_TARGET = "2";

    public static final Double GOODS_MKT_PRICE = 159900D;

    public static final Double AVG_SUPPLY_PRICE = 0.03;

    /**
     * 国际化配置文件里当前语言
     */
    public static final String LOCALE_CODE = "Locale";

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
        CREATE_DATE_DESC(1, "t.CREATE_DATE DESC"),

        CREATE_DATE_ASC(2, "t.CREATE_DATE ASC"),

        PRICE_DESC(3, "t.MKTPRICE DESC"),

        PRICE_ASC(4, "t.MKTPRICE ASC"),

        BUY_COUNT_DESC(5, "t.BUY_COUNT DESC"),

        BUY_COUNT_ASC(6, "t.BUY_COUNT ASC"),

        DELIVERY_PRICE_DESC(7, "DELIVERY_PRICE DESC"),

        DELIVERY_PRICE_ASC(8, "DELIVERY_PRICE ASC"),

        MKTPRICE_DESC(9, "MKTPRICE DESC"),

        MKTPRICE_ASC(10, "MKTPRICE ASC"),

        DELIVERY_PRICE_ASC_MERCHANT_TYPE_ASC(11, "DELIVERY_PRICE ASC,MERCHANT_TYPE ASC");

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

    /**
     * 状态常量
     */
    public enum TagTypeEnum {
        GOODS_TAG("01","商品标签"),
        CHANNEL_TAG("02","渠道标签"),
        PRODUCT_TAG("03","产品标签");

        private String code;
        private String value;

        TagTypeEnum(String code, String value){
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
     * 状态常量
     */
    public enum AuditStateEnum {
        WAIT_SUBMIT("1","待提交"),
        AUDITING("2","审核中"),
        AUDITED("3","审核通过"),
        NOT_AUDITED("4","审核不通过");

        private String code;
        private String value;

        AuditStateEnum(String code, String value){
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
     * 状态常量
     */
    public enum IsAllotEnum {
        IS_NOT_ALLOT(0,"未分货"),
        IS_ALLOT(1,"分货");

        private Integer code;
        private String value;

        IsAllotEnum(Integer code, String value){
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
     * 是否预售
     */
    public enum IsAdvanceSale {
        /**
         * 非预售商品
         */
        IS_NOT_ADVANCE_SALE(0,"非预售商品"),
        /**
         * 预售商品
         */
        IS_ADVANCE_SALE(1,"预售商品");

        private Integer code;
        private String value;

        IsAdvanceSale(Integer code, String value){
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
     * 是否前置补贴
     */
    public enum IsSubsidy {
        /**
         * 非前置补贴商品
         */
        IS_NOT_SUBSIDY(0,"非前置补贴商品"),
        /**
         * 前置补贴商品
         */
        IS_SUBSIDY(1,"前置补贴商品");

        private Integer code;
        private String value;

        IsSubsidy(Integer code, String value){
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
}
