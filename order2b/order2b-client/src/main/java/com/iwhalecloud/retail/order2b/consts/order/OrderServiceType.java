package com.iwhalecloud.retail.order2b.consts.order;

import com.iwhalecloud.retail.order2b.annotation.EnumInterface;
import org.springframework.util.StringUtils;

/**
 * ServiceType(ord_order_apply)
 */
public enum OrderServiceType implements EnumInterface {
    NULL("","",""),
    ORDER_SHIP_TYPE_1("订购关系","1",""),
    ORDER_SHIP_TYPE_2("退费关系","2",""),
    ORDER_SHIP_TYPE_3("换货关系","3",""),
    ORDER_SHIP_TYPE_4("退货关系","4",""),
            ;


    public static OrderServiceType matchOpCode(String opCodeStr) {
        if(StringUtils.isEmpty(opCodeStr)){
            return OrderServiceType.NULL;
        }
        for (OrderServiceType opCode : OrderServiceType.values()) {
            if (opCode.getCode().equalsIgnoreCase(opCodeStr)) {
                return opCode;
            }
        }
        return OrderServiceType.NULL;
    }

    OrderServiceType(String name, String code, String desc) {
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
