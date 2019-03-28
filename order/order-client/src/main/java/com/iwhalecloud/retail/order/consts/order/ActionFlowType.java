package com.iwhalecloud.retail.order.consts.order;


import org.springframework.util.StringUtils;

public enum ActionFlowType implements EnumInterface {
    NULL("-1", "操作类型不匹配", "操作类型不匹配"),
    ORDER_HANDLER_XD("下单", "B", ""),
    ORDER_HANDLER_ZF("支付", "C", ""),
    ORDER_HANDLER_FH("发货", "H", ""),
    ORDER_HANDLER_SH("确认收货", "J", ""),
    ORDER_HANDLER_QX("取消", "QX", ""),
    ORDER_HANDLER_PJ("评价", "PJ", ""),
    ORDER_HANDLER_SC("删除", "SC", ""),
    ORDER_HANDLER_CHRR("串码录入", "CHRR", ""),
    ORDER_HANDLER_LZRR("揽装录入", "LZRR", ""),
    ORDER_HANDLER_DDBL("订单补录", "DDBL", "(合约机)"),;

    public static ActionFlowType matchOpCode(String opCodeStr) {
        if (StringUtils.isEmpty(opCodeStr)) {
            return ActionFlowType.NULL;
        }
        for (ActionFlowType opCode : ActionFlowType.values()) {
            if (opCode.getCode().equalsIgnoreCase(opCodeStr)) {
                return opCode;
            }
        }
        return ActionFlowType.NULL;
    }

    ActionFlowType(String name, String code, String desc) {
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
