package com.iwhalecloud.retail.order2b.consts;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/4/24 19:36
 * @description 采购申请单常量
 */
public class PurApplyConsts {

    /**
     * 修改申请单
     */
    public final static String PUR_APPLY_SAVE = "1";

    /**
     * 新增申请单
     */
    public final static String PUR_APPLY_SUBMIT = "2";
    /**
     * 编辑申请单
     */
    public final static String PUR_APPLY_EDIT = "3";

    /**
     * 申请单类型：采购申请单
     */
    public final static String PUR_APPLY_TYPE = "10";

    /**
     * 申请单类型：采购单
     */
    public final static String PURCHASE_TYPE = "20";

    /**
     * 采购申请单审核流程
     */
    public final static String PUR_APPLY_AUDIT_PROCESS_ID = "17";

    /**
     * 采购申请单审核流程
     */
    public final static String PUR_APPLY_AUDIT_SGS_PROCESS_ID = "528";

    /**
     * 政企价格修改审核流程
     */
    public final static String PROD_PRODUCT_CORPORATION_PRICE_ID = "20190611";
    
    /**
     *固网终端 政企价格修改审核流程
     */
    public final static String GWPROD_PRODUCT_CORPORATION_PRICE_ID = "2019061501";
    
    /**
     *移动终端 政企价格修改审核流程
     */
    public final static String YDPROD_PRODUCT_CORPORATION_PRICE_ID = "2019061401";

    /**
     * 采购申请单审核流程
     */
    public final static String PURCHASE_AUDIT_PROCESS_ID = "18";

    /**
     * 发货类型
     */
    public final static String DELIVERY_TYPE = "1";

    /**
     * 申请单状态：审核通过
     */
    public final static String PUR_APPLY_STATUS_PASS = "40";

    /**
     * 申请单状态省公司审核 ：审核通过
     */
    public final static String SGS_PUR_APPLY_STATUS_PASS = "20";

    /**
     * 政企价格修改审核通过
     */
    public final static String AUDIT_STATE_PASS = "3";
    
    /**
     * 审核中
     */
    public final static String AUDIT_STATE_WAIT = "2";
    
    /**
     * 政企价格修改审核不通过
     */
    public final static String AUDIT_STATE_NO_PASS = "4";
    
    /**
     * 申请单状态：审核不通过
     */
    public final static String PUR_APPLY_STATUS_NOT_PASS = "80";


    /**
     * 申请单状态：待收货
     */
    public final static String PUR_APPLY_STATUS_RECEIVED = "50";

    /**
     * 申请单状态：已完成
     */
    public final static String PUR_APPLY_STATUS_FINISHED = "60";

    /**
     * 状态：待收货
     */
    public final static String PUR_APPLY_STATUS_DELIVERY = "5";

    /**
     * 状态：已收货
     */
    public final static String PUR_APPLY_MKT_STATUS_RECEIVED = "6";

    /**
     * 状态：发货中
     */
    public final static String PUR_APPLY_STATUS_DELIVERYING = "41";
}

