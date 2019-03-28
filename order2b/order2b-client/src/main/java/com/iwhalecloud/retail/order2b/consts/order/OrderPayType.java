package com.iwhalecloud.retail.order2b.consts.order;

import com.iwhalecloud.retail.order2b.annotation.EnumInterface;
import org.springframework.util.StringUtils;

/**
 */
public enum OrderPayType implements EnumInterface {

    NULL("","",""),
    PAY_TYPE_1("翼支付","1",""),
    PAY_TYPE_3("线下支付","3",""),
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
