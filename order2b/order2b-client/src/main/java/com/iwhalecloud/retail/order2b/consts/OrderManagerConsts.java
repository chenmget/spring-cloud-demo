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

    public final static String ORDER_CAT_1="1";//预售订单
    public final static String ORDER_CAT_0="0";//普通订单


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


}
