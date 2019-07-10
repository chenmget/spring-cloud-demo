package com.iwhalecloud.retail.goods2b.common;

public class GoodsRulesConst {

    // 商品带分货规则
    public static final String GOODS_WITH_RULES = "1";

    // 商品不带分货规则
    public static final String GOODS_WITHOUT_RULES = "0";

    public enum Stockist {
        // 经营主体类型
        BUSINESS_ENTITY_TYPE("BUSINESS_ENTITY_TYPE","1"),
        // 店中商类型编码
        PARTNER_IN_SHOP_TYPE("PARTNER_IN_SHOP_TYPE","2"),
        //地包商
        DB_CODE("PARTNER_IN_SHOP_TYPE","3");

        private String key;
        private String value;
        Stockist(String key,String value){
            this.key = key;
            this.value = value;
        }
        public String getKey(){
            return key;
        }
        public String getValue(){
            return value;
        }
    }

    public enum state {
        // 生效
        EFF("EFF","1"),
        // 失效
        EXP("EXP","0");

        private String key;
        private String value;
        state(String key,String value){
            this.key = key;
            this.value = value;
        }
        public String getKey(){
            return key;
        }
        public String getValue(){
            return value;
        }
    }



}
