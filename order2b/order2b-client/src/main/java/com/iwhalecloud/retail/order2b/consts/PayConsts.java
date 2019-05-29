package com.iwhalecloud.retail.order2b.consts;

public class PayConsts {
    /**
     * '记录应用类型：B2B：hnyhj_b2b；B2C：hnyhj_b2c；收钱哆：hnsqd',
     */
    public final static String PLATFORM_TYPE_HNYHJ_B2B = "B2B";
    public final static String PLATFORM_TYPE_B2C = "B2C";
    public final static String PLATFORM_TYPE_HNYHJ_B2C = "hnyhj_b2c";
    public final static String PLATFORM_TYPE_HNSQD = "hnsqd";

    /**
     * 记录支付类型：1001 线下支付，1002 扫码支付，1003 POS支付，1004 收银台支付，1005 云货架2B线下支付,1006 翼支付预授权支付
     */
    public final static String REQUEST_TYPE_1001 = "1001";
    public final static String REQUEST_TYPE_1002 = "1002";
    public final static String REQUEST_TYPE_1003 = "1003";
    public final static String REQUEST_TYPE_1004 = "1004";
    public final static String REQUEST_TYPE_1005 = "1005";
    public final static String REQUEST_TYPE_1006 = "1006";
    /**
     * 用于区分支付对接平台：1001 翼支付，1002 巨龙支付，1003 翼支付企业版
     */
    public final static String PAY_PLATFORM_ID_1001 = "1001";
    public final static String PAY_PLATFORM_ID_1002 = "1002";
    public final static String PAY_PLATFORM_ID_1003 = "1003";

    /**
     * 记录支付接口类型：1001 聚合支付码GET方式，1002 聚合支付码POST方式，1003 收银台支付（专用于云货架2B支付），1004 线下支付,1006 翼支付
     */
    public final static String PAY_TYPE_1001 = "1001";
    public final static String PAY_TYPE_1002 = "1002";
    public final static String PAY_TYPE_1003 = "1003";
    public final static String PAY_TYPE_1004 = "1004";
    public final static String PAY_TYPE_1006 = "1006";

    /**
     * 用于区分付费/退费：1001 收费，1002 退费，1003 预付费
     */
    public final static String OPERATION_TYPE_1001 = "1001";
    public final static String OPERATION_TYPE_1002 = "1002";
    public final static String OPERATION_TYPE_1003 = "1003";

    /**
     * 用于区分客户支付终端：1001 二维码，1002 POS，1003 其他
     */
    public final static String TERMINAL_TYPE_1001 = "1001";
    public final static String TERMINAL_TYPE_1002 = "1002";
    public final static String TERMINAL_TYPE_1003 = "1003";

    /**
     * 用于登记订单支付状态：0 订单生成，1 支付中，2 支付成功， 3 业务处理完成，4 支付失败，5 用户已撤销，6、超时未支付，7 已退款
     */
    public final static String PAY_STATUS_0 = "0";
    public final static String PAY_STATUS_1 = "1";
    public final static String PAY_STATUS_2 = "2";
    public final static String PAY_STATUS_3 = "3";
    public final static String PAY_STATUS_4 = "4";
    public final static String PAY_STATUS_5 = "5";
    public final static String PAY_STATUS_6 = "6";
    public final static String PAY_STATUS_7 = "7";

    /**
     * 付款情况： -1 未付款， 1 已付款
     */
    public final static String UN_PAY = "-1";
    public final static String PAYED = "1";

}