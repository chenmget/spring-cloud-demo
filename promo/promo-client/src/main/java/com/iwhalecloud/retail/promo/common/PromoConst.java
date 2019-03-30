package com.iwhalecloud.retail.promo.common;

/**
 * @author mzl
 * @date 2018/10/30
 */
public class PromoConst {

    public static final Integer UNDELETED = 0;
    public static final Integer DELETED = 1;
    public static final String NEXTNODE_ID = "1013";
    public static final String ROUTE_ID = "25";

    /**
     * 营销活动状态
     */
    public enum STATUSCD {
        STATUS_CD_1("1","已保存"),
        STATUS_CD_10("10","待审核"),
        STATUS_CD_20("20","审核通过"),
        STATUS_CD_30("40","已终止"),
        STATUS_CD_PLUS_20("30","审核不通过"),
        STATUS_CD_PLUS_1("0","已取消");

        private String code;
        private String name;

        STATUSCD(String code,String name) {
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
    }

    /**
     * 活动类型
     */
    public enum ACTIVITYTYPE{
        BOOKING("1001","预售"),
        PRESUBSIDY("1002","前置补贴"),
        REBATE("1003","返利");
        private String code;
        private String name;

        ACTIVITYTYPE(String code,String name) {
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
    }

    /**
     * 优惠类型
     */
    public enum PromotionType {
        PROMOTION_TYPE_CD_10("10","减免"),
        PROMOTION_TYPE_CD_20("20","券"),
        PROMOTION_TYPE_CD_30("30","返利"),
        PROMOTION_TYPE_CD_40("40","赠送"),
        PROMOTION_TYPE_CD_50("50","红包");

        private java.lang.String code;
        private java.lang.String name;

        PromotionType(String code,String name) {
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
    }

    /**
     * 是否删除状态
     */
    public enum IsDelete {
        IS_DELETE_CD_0("0","未删"),
        IS_DELETE_CD_1("1","删除");

        private java.lang.String code;
        private java.lang.String name;

        IsDelete(String code,String name) {
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
    }

    /**
     * 活动对象类型
     */
    public enum ActivityScopeType {
        ACTIVITY_SCOPE_TYPE_10("10","地区"),
        ACTIVITY_SCOPE_TYPE_20("20","商家");

        private java.lang.String code;
        private java.lang.String name;

        ActivityScopeType(String code,String name) {
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
    }

    /**
     * 是否删除状态
     */
    public enum ActivityParticipantType {
        ACTIVITY_PARTICIPANT_TYPE_10("10","地区"),
        ACTIVITY_PARTICIPANT_TYPE_20("20","商家");

        private java.lang.String code;
        private java.lang.String name;

        ActivityParticipantType(String code,String name) {
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
    }

    /**
     * 前置补贴活动补录状态
     */
    public enum ActivitySupStatus {
        ACTIVITY_SUP_STATUS_PEDING("0","待审核"),
        ACTIVITY_SUP_STATUS_SUCCESS("1","审核通过"),
        ACTIVITY_SUP_STATUS_ERROR("-1","审核不通过"),
        ACTIVITY_SUP_STATUS_CANCEL("-9","已取消");


        private java.lang.String code;
        private java.lang.String name;

        ActivitySupStatus(String code,String name) {
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
    }

    /**
     * 前置补贴活动补录状态
     */
    public enum PayType {
        PAY_TYPE_1("1","预付定金"),
        PAY_TYPE_2("2","付全款");


        private java.lang.String code;
        private java.lang.String name;

        PayType(String code,String name) {
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
    }
    /**
     * 返利计算规则
     */
    public enum CALCULATIONRULE{
        CALCULATION_RULE_10("10","单台返"),
        CALCULATION_RULE_20("20","达X台，每台返Y元"),
        CALCULATION_RULE_30("30","达X1台，每台返Y1元；达X2台，每台返Y2元……"),
        CALCULATION_RULE_40("40","每达X台返Y元"),
        CALCULATION_RULE_50("50","达X台返Y元");

        private java.lang.String code;
        private java.lang.String name;

        CALCULATIONRULE(String code,String name){
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
    }

    /**
     * 活动结算记录状态
     */
    public enum OperationType {
        OPERATION_TYPE_1("1","新增"),
        OPERATION_TYPE_2("2","退库");


        private java.lang.String code;
        private java.lang.String name;

        OperationType(String code,String name) {
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
    }

    /**
     * 活动结算记录状态
     */
    public enum SettleMode {
        SETTLE_MODE_1("1","结算给零售商"),
        SETTLE_MODE_2("2","结算给地包商");


        private java.lang.String code;
        private java.lang.String name;

        SettleMode(String code,String name) {
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
    }

    /**
     * 返利结算时间
     */
    public enum SETTLEMENT{
        SETTLEMENT_10("10","订单支付"),
        SETTLEMENT_20("20","卖家发货"),
        SETTLEMENT_30("30","买家确认收货");
        private java.lang.String code;
        private java.lang.String name;

        SETTLEMENT(String code,String name){
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
    }
}
