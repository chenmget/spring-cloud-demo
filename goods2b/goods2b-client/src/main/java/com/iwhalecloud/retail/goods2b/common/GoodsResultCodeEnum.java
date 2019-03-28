package com.iwhalecloud.retail.goods2b.common;

import com.iwhalecloud.retail.dto.ResultCode;

/**
 * 返回值枚举类
 *
 * @author mzl
 * @date 2018/9/30
 */
public enum GoodsResultCodeEnum implements ResultCode {

    SUCCESS("0","SUCCESS"),
    ERROR("-1","ERROR"),
    INVOKE_PARTNER_SERVICE_EXCEPTION("1000","调用供应商服务异常"),
    INVOKE_WAREHOUSE_SERVICE_EXCEPTION("1001","调用库存服务异常"),
    INVOKE_SYSTEM_SERVICE_EXCEPTION("1003","调用系统服务异常"),
    NOT_LOGIN("1006","用户未登录"),
    DESERIALIZE_ERROR("1007","反序列化失败"),
    NOT_HAS_STOCK("1008","不存在库存"),
    GOODS_RULE_VALID_FAIL("1009","分货规则校验不通过"),
    GOODS_RULE_VALID_FAIL_EXIST_OVERLAPPING_RECORDS("1010","存在经营主体和零售商交叉的记录"),
    MERCHANT_ACCOUNT_IS_EMPTY("1011","供应商账户为空");

    private final String code;
    private final String desc;


    GoodsResultCodeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode(){
        return code;
    }

    @Override
    public String getDesc(){
        return desc;
    }

}
