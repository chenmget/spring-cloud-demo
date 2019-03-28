package com.iwhalecloud.retail.order2b.consts.order;

import com.iwhalecloud.retail.order2b.annotation.EnumInterface;
import org.springframework.util.StringUtils;

/**
 */
public enum TypeStatus implements EnumInterface {

    NULL("","","",""),

    /**
     * UpdateOrderStatusRequest(confirmType)
     */
    TYPE_20("不通过","2","","0"),
    TYPE_10("通过","1","","0"),

    /**
     * ord_order(pay_status)
     */
    TYPE_01("已付款","1","","1"),
    TYPE_11("未付款","0","","1"),

    /**
     * ord_order(ship_status)
     */
    TYPE_02("已发货","1","","2"),
    TYPE_12("未发货","0","","2"),

    TYPE_14("正常","1","","4"),
    TYPE_34("换货","3","","4"),

            ;

    public final static String CONFIRM ="0";
    public final static String PAY="1";
    public final static String SHIP="2";
    public final static String APPLY="3";
    public final static String APPLY_HH="4";

    public static TypeStatus matchOpCode(String opCodeStr,String type) {
        if(StringUtils.isEmpty(opCodeStr)){
            return TypeStatus.NULL;
        }
        for (TypeStatus opCode : TypeStatus.values()) {
            if(opCode.getType().equalsIgnoreCase(type)){
                if (opCode.getCode().equalsIgnoreCase(opCodeStr)) {
                    return opCode;
                }
            }

        }
        return TypeStatus.NULL;
    }

    TypeStatus(String name, String code, String desc,String type) {
        this.code = code;
        this.name = name;
        this.type= type;
        this.desc = desc;
    }

    private String code;
    private String name;
    private String desc;
    private String type;

    private String getType() {
        return type;
    }

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
