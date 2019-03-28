package com.iwhalecloud.retail.order2b.consts.order;

import com.iwhalecloud.retail.order2b.annotation.EnumInterface;
import org.springframework.util.StringUtils;

/**
 */
public enum CouponType implements EnumInterface {


    NULL("默认","0","0"),
    COUPON_TYPE_1("卡券","1",""),
    COUPON_TYPE_2("红包","2",""),
    COUPON_TYPE_3("返利","3",""),
    COUPON_TYPE_4("价保款","4",""),
    ;


    public static CouponType matchOpCode(String opCodeStr) {
        if(StringUtils.isEmpty(opCodeStr)){
            return CouponType.NULL;
        }
        for (CouponType opCode : CouponType.values()) {
            if (opCode.getCode().equalsIgnoreCase(opCodeStr)) {
                return opCode;
            }
        }
        return CouponType.NULL;
    }

    CouponType(String name, String code, String desc) {
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

