package com.iwhalecloud.retail.order2b.consts.order;


import com.iwhalecloud.retail.order2b.annotation.EnumInterface;
import org.springframework.util.StringUtils;

public enum ActionFlowType implements EnumInterface {
    NULL("-1", "操作类型不匹配", "操作类型不匹配"),

    /**
     * 操作
     */
    ORDER_HANDLER_DXSH("电信审核", "DXSH", ""),
    ORDER_HANDLER_SL("受理", "SL", ""),
    ORDER_HANDLER_QX("取消", "QX", ""),
    ORDER_HANDLER_SC("删除", "SC", ""),
    ORDER_HANDLER_SQGB("卖家申请关闭", "SQGB", ""),
    ORDER_HANDLER_GB("已关闭", "GB", ""),

    /**
     * 下单
     * (下单-[商家确认订单]-支付-发货-确认收货-评价)
     */
    ORDER_HANDLER_XD("下单", "B", ""),
    ORDER_HANDLER_SJXR("商家确认订单", "SJQR", ""),
    ORDER_HANDLER_ZF("支付", "C", ""),
    ORDER_HANDLER_FH("发货", "H", ""),
    ORDER_HANDLER_SH("确认收货", "J", ""),
    ORDER_HANDLER_PJ("评价", "PJ", ""),

    /**
     * 退款
     * (退款申请-退款审核-商家退款-退款完成)
     */
    ORDER_HANDLER_TKSQ("退款申请", "TKSQ", ""),
    ORDER_HANDLER_TKSH("退款审核", "TKSH", ""),
    ORDER_HANDLER_SJTK("商家退款", "SJTK", ""),
    ORDER_HANDLER_TKWC("退款完成", "TKWC", "买家确认收款"),

    /**
     * 退款退货
     * (退款申请-退款审核-买家退货-退货商家收货-商家退款-退款完成)
     */
    ORDER_HANDLER_MJTH("买家退货", "MJTH", ""),
    ORDER_HANDLER_THSH("商家收货", "THSH", ""),

    /**
     * 换货
     * (换货申请-换货审核-买家退货-商家收货—商家发货-换货完成)
     */
    ORDER_HANDLER_HHSQ("换货申请", "HHSQ", ""),
    ORDER_HANDLER_HHSH("换货审核", "HHSH", ""),
    ORDER_HANDLER_MJFH("商家发货", "MJFH", ""),
    ORDER_HANDLER_HHWC("换货完成", "HHWC", "买家确认收货"),

    /**
     * 预售单
     */
    ORDER_HANDLER_DJZF("定金支付", "DJZF", ""),
    ORDER_HANDLER_WKZF("尾款支付", "WKZF", ""),

    ;

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
