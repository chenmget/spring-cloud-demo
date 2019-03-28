package com.iwhalecloud.retail.order.consts.order;

import org.springframework.util.StringUtils;

/**
 * 支付方式
 */
public enum OrderPayType implements EnumInterface {

    NULL("","",""),
    ORDER_PAY_CODE_1("在线支付","1",""),
    ORDER_PAY_CODE_2("货到付款","2",""),
    ORDER_PAY_CODE_3("线下支付","3",""),
            ;

    public static OrderPayType matchOpCode(String opCodeStr) {
        if(StringUtils.isEmpty(opCodeStr)){
            return OrderPayType.NULL;
        }
        for (OrderPayType opCode : OrderPayType.values()) {
            if (opCode.getCode().equalsIgnoreCase(opCodeStr)) {
                return opCode;
            }
        }
        return OrderPayType.NULL;
    }

    OrderPayType(String name, String code, String desc) {
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

    @Override
    public String getEnumObj() {
        return getCode();
    }
}
