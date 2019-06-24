package com.iwhalecloud.retail.order2b.consts;


public class OrderManagerConsts {


    /**
     * 绑定信息
     */
    public final static String ORDER_BIND_TYPE_0 ="0"; //普通（默认）
    public final static String ORDER_BIND_TYPE_1 ="1"; //合约机

    /**
     * 购买方式(sourceType)
     */
    public final static String ORDER_SOURCE_TYPE_LJGM = "LJGM"; //立即购买
    public final static String ORDER_SOURCE_TYPE_GWC = "GWCGM";  //购物车购买

//    public final static String ORDER_CAT_1="1";//预售订单
//    public final static String ORDER_CAT_0="0";//普通订单


    public final static String ADVANCE_ORDER_PAY_1="1";//支付定金
    public final static String ADVANCE_ORDER_PAY_2="2";//全款支付



    public final static String USER_EXPORT_TYPE_1 ="1"; //采购 我申请的
    public final static String USER_EXPORT_TYPE_2 ="2"; //销售 我要处理的
    public final static String USER_EXPORT_TYPE_3 ="3"; //管理


    public final static String IS_MERCHANT_CONFIRM ="1";

    /**
     * 回滚操作
     */
    public final static Integer UPDATE_ROLLBACK=-1;
    /**
     * 正项操作
     */
    public final static Integer UPDATE_PUSH=1;

    /**
     * 活动类型（1001--预售  1002--前置补贴 1003--返利）
     */
    public final static String ACTIVITY_TYPE_1002="1002";

    /**
     * 国际化配置文件里当前语言
     */
    public static final String LOCALE_CODE = "Locale";


    /**
     * 订单类型
     */
    public enum ORDER_CAT{
        ORDER_CAT_0("0","普通订单（既不预售又不分货）"),
        ORDER_CAT_1("1"," 预售订单（预售不分货）"),
        ORDER_CAT_2("2","分货订单（分货不预售）"),
        ORDER_CAT_3("3","预售分货（既预售又分货）"),
        ORDER_CAT_4("4","定向分货（商品表TARGET_TYPE 按对象，普通订单的子项）"),
        ORDER_CAT_5("5","直供订单");
        private String code;
        private String name;

        ORDER_CAT(String code,String name) {
            this.code = code;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

    }
}
