package com.iwhalecloud.retail.order.consts.order;

import org.springframework.util.StringUtils;

public enum OrderAllStatus {
    NULL("","",""),
    ORDER_STATUS_2("待支付","2",""),
    ORDER_STATUS_4("待发货","4",""),
    ORDER_STATUS_5("待收货确认","5",""),
    ORDER_STATUS_7("收货确认","7","（待评价）"),
    ORDER_STATUS_6("完成","6",""),
    ORDER_STATUS_99("异常订单","99",""),
    ORDER_STATUS_1_("待支付","2",""),
    ORDER_STATUS_10_("取消完成","-10",""),
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
