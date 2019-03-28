package com.iwhalecloud.retail.order.consts.order;

import org.springframework.util.StringUtils;

/**
 * 发货方式(shipType)
 */
public enum  OrderShipType implements EnumInterface{

    NULL("","",""),
    ORDER_SHIP_TYPE_1("快递发货","1",""),
    ORDER_SHIP_TYPE_2("网点自提","2",""),
            ;


    public static OrderShipType matchOpCode(String opCodeStr) {
        if(StringUtils.isEmpty(opCodeStr)){
            return OrderShipType.NULL;
        }
        for (OrderShipType opCode : OrderShipType.values()) {
            if (opCode.getCode().equalsIgnoreCase(opCodeStr)) {
                return opCode;
            }
        }
        return OrderShipType.NULL;
    }

    OrderShipType(String name, String code, String desc) {
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
