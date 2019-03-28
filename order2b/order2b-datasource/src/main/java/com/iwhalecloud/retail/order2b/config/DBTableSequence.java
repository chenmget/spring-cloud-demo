package com.iwhalecloud.retail.order2b.config;


import org.springframework.util.StringUtils;

public enum DBTableSequence {

    NULL("", ""),
    ORD_ADVANCE_ORDER("seq_ord_advance_order_id", "预售信息"),
    ORD_CART("seq_ord_cart_cart_id", "购物车"),
    ORD_DELIVERY("seq_ord_delivery_delivery_id", "物流"),
    ORD_ORDER("seq_ord_order_order_id", "订单主表"),
    ORD_ORDER_APPLY("seq_ord_order_apply_apply_id", "售后申请单"),
    ORD_ORDER_APPLY_DETAIL("seq_ord_order_apply_detail_id", "售后申请单串码"),
    ORD_ORDER_CONTRACT_INFO("seq_ord_order_contract_info_id", "合约信息"),
    ORD_ORDER_ITEMS("seq_ord_order_items_item_id", "订单项"),
    ORD_ORDER_ITEM_DETAIL("seq_ord_order_item_detail_id", "订单单项串码"),
    ORD_ORDER_LOG("seq_ord_order_log_log_id", "日志"),
    ORD_ORDER_RECOMMENDER("seq_ord_order_recommender_id", "我也不知道"),
    ORD_ORDER_Z_FLOW("seq_ord_order_z_flow_flow_id", "工作流"),
    ORD_PROMOTION("seq_ord_promotion_promotio_id", "优惠");

    public static DBTableSequence matchOpCode(String opCodeStr) {
        if (StringUtils.isEmpty(opCodeStr)) {
            return DBTableSequence.NULL;
        }
        for (DBTableSequence opCode : DBTableSequence.values()) {
            if (opCode.getCode().equalsIgnoreCase(opCodeStr)) {
                return opCode;
            }
        }
        return DBTableSequence.NULL;
    }

    DBTableSequence(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String name;

    private String code;
}
