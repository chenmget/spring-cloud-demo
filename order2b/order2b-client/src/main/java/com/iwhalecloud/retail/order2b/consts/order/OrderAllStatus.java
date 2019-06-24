package com.iwhalecloud.retail.order2b.consts.order;

import org.springframework.util.StringUtils;

//初始状态：0
//
//        待审核：11
//        待卖家确认：12；
//        待支付：2；
//        已支付、待受理：3 ；
//        已受理待发货：4 ；
//        已发货待确认：5 ；
//        确认收货：6 ；
//        已完成：10；
//        审核不通过：-11
//        卖家确认不通过：-12
//        作废：-8 ；
//        撤单：-9 ；
//        取消订单：-10 ；
//        订单异常：99 ；
public enum OrderAllStatus {
    NULL("","",""),
    ORDER_STATUS_11("待审核","11",""),
    ORDER_STATUS_11_("审核不通过","-11",""),
    ORDER_STATUS_12("待卖家确认","12",""),
    ORDER_STATUS_12_("卖家确认不通过","-12",""),
    ORDER_STATUS_2("待支付","2",""),
    ORDER_STATUS_13("待支付定金","13",""),
    ORDER_STATUS_14("待支付尾款","14",""),
    ORDER_STATUS_3("已支付、待受理","3",""),
    ORDER_STATUS_4("已受理待发货","4",""),
    ORDER_STATUS_4_("已关闭","-4",""),
    ORDER_STATUS_41("部分发货","41",""),
    ORDER_STATUS_5("待收货确认","5",""),
    ORDER_STATUS_6("收货确认","6","（待评价）"),
    ORDER_STATUS_10("完成","10",""),
    ORDER_STATUS_8_("作废","-8",""),
    ORDER_STATUS_9_("撤单","-9",""),
    ORDER_STATUS_10_("取消完成","-10",""),
    ORDER_STATUS_99("异常订单","99",""),

    ORDER_STATUS_1_("已退货","-1",""),
    ORDER_STATUS_2_("已退款","-2",""),
    ORDER_STATUS_3_("已换货","-3",""),

    ORDER_STATUS_21("待审核","21",""),
    ORDER_STATUS_21_("审核不通过","-21",""),

    ORDER_STATUS_22("待买家退货","22",""),
    ORDER_STATUS_23("待商家收货","23",""),

    ORDER_STATUS_24("待商家发货","24",""),
    ORDER_STATUS_25("待买家收货","25",""),

    ORDER_STATUS_28("待商家退款","28",""),
    ORDER_STATUS_29("待买家确认收款","29",""),

    ORDER_STATUS_31("卖家申请关闭","31",""),

    ;



    public static OrderAllStatus matchOpCode(String opCodeStr) {
        if(StringUtils.isEmpty(opCodeStr)){
            return OrderAllStatus.NULL;
        }
        for (OrderAllStatus opCode : OrderAllStatus.values()) {
            if (opCode.getCode().equalsIgnoreCase(opCodeStr)) {
                return opCode;
            }
        }
        return OrderAllStatus.NULL;
    }

    OrderAllStatus(String name, String code, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    private String code;
    private String name;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
