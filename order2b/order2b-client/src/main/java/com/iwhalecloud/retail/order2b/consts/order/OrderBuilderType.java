package com.iwhalecloud.retail.order2b.consts.order;

import com.iwhalecloud.retail.order2b.annotation.EnumInterface;
import org.springframework.util.StringUtils;

/**
 * 发货方式(shipType)
 */
public enum OrderBuilderType implements EnumInterface {

    NULL("","",""),
    ORDER_TYPE_1("B2C下单","1",""),
    ORDER_TYPE_2("B2B下单","2",""),
    ORDER_TYPE_11("省包至地包交易订单","11",""),
    ORDER_TYPE_12("省包至零售商交易订单","12",""),
    ORDER_TYPE_13("地包到零售商交易订单","13",""),
    ORDER_TYPE_14("强制分货零售商订单","14",""),
    ORDER_TYPE_20("零售商到客户交易订单","20",""),
    ORDER_TYPE_21("分销交易订单","21",""),
            ;


    public static OrderBuilderType matchOpCode(String opCodeStr) {
        if(StringUtils.isEmpty(opCodeStr)){
            return OrderBuilderType.NULL;
        }
        for (OrderBuilderType opCode : OrderBuilderType.values()) {
            if (opCode.getCode().equalsIgnoreCase(opCodeStr)) {
                return opCode;
            }
        }
        return OrderBuilderType.NULL;
    }

    OrderBuilderType(String name, String code, String desc) {
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
