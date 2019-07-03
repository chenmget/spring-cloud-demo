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
     * 国际化配置文件里当前语言
     */
    public static final String LOCALE_CODE = "Locale";

    /**
     * 营销活动状态
     */
    public enum STATUSCD {
        STATUS_CD_1("1", "已保存"),
        STATUS_CD_10("10", "待审核"),
        STATUS_CD_20("20", "审核通过"),
        STATUS_CD_30("40", "已终止"),
        STATUS_CD_PLUS_20("30", "审核不通过"),
        STATUS_CD_PLUS_1("0", "已取消");

        private String code;
        private String name;

        STATUSCD(String code, String name) {
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
    public enum ACTIVITYTYPE {
        BOOKING("1001", "预售"),
        PRESUBSIDY("1002", "前置补贴"),
        REBATE("1003", "返利");
        private String code;
        private String name;

        ACTIVITYTYPE(String code, String name) {
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
        PROMOTION_TYPE_CD_10("10", "减免"),
        PROMOTION_TYPE_CD_20("20", "券"),
        PROMOTION_TYPE_CD_30("30", "返利"),
        PROMOTION_TYPE_CD_40("40", "赠送"),
        PROMOTION_TYPE_CD_50("50", "红包");

        private java.lang.String code;
        private java.lang.String name;

        PromotionType(String code, String name) {
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
        IS_DELETE_CD_0("0", "未删"),
        IS_DELETE_CD_1("1", "删除");

        private java.lang.String code;
        private java.lang.String name;

        IsDelete(String code, String name) {
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
        ACTIVITY_SCOPE_TYPE_10("10", "地区"),
        ACTIVITY_SCOPE_TYPE_20("20", "商家");

        private java.lang.String code;
        private java.lang.String name;

        ActivityScopeType(String code, String name) {
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
        ACTIVITY_PARTICIPANT_TYPE_10("10", "地区"),
        ACTIVITY_PARTICIPANT_TYPE_20("20", "商家"),
        ACTIVITY_PARTICIPANT_TYPE_30("30", "按条件筛选");

        private java.lang.String code;
        private java.lang.String name;

        ActivityParticipantType(String code, String name) {
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
        ACTIVITY_SUP_STATUS_PEDING("0", "待审核"),
        ACTIVITY_SUP_STATUS_SUCCESS("1", "审核通过"),
        ACTIVITY_SUP_STATUS_ERROR("-1", "审核不通过"),
        ACTIVITY_SUP_STATUS_CANCEL("-9", "已取消");


        private java.lang.String code;
        private java.lang.String name;

        ActivitySupStatus(String code, String name) {
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
        PAY_TYPE_1("1", "预付定金"),
        PAY_TYPE_2("2", "付全款");


        private java.lang.String code;
        private java.lang.String name;

        PayType(String code, String name) {
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
    public enum CALCULATIONRULE {
        CALCULATION_RULE_10("10", "单台返"),
        CALCULATION_RULE_20("20", "达X台，每台返Y元"),
        CALCULATION_RULE_30("30", "达X1台，每台返Y1元；达X2台，每台返Y2元……"),
        CALCULATION_RULE_40("40", "每达X台返Y元"),
        CALCULATION_RULE_50("50", "达X台返Y元");

        private java.lang.String code;
        private java.lang.String name;

        CALCULATIONRULE(String code, String name) {
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
        OPERATION_TYPE_1("1", "新增"),
        OPERATION_TYPE_2("2", "退库");


        private java.lang.String code;
        private java.lang.String name;

        OperationType(String code, String name) {
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
        SETTLE_MODE_1("1", "结算给零售商"),
        SETTLE_MODE_2("2", "结算给地包商");


        private java.lang.String code;
        private java.lang.String name;

        SettleMode(String code, String name) {
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
    public enum SETTLEMENT {
        SETTLEMENT_10("10", "订单支付"),
        SETTLEMENT_20("20", "卖家发货"),
        SETTLEMENT_30("30", "买家确认收货");
        private java.lang.String code;
        private java.lang.String name;

        SETTLEMENT(String code, String name) {
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
     * 活动产品配置数量限制
     */
    public enum ProductNumFlg {
        ProductNumFlg_0("0", "不限制"),
        ProductNumFlg_1("1", "限制");
        private java.lang.String code;
        private java.lang.String name;

        ProductNumFlg(String code, String name) {
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
     * 活动变更模型审核状态枚举
     */
    public enum AuditState {
        AuditState_1("1","待提交"),
        AuditState_2("2","审核中"),
        AuditState_3("3","初审通过"),
        AuditState_4("4","初审不通过"),
        AuditState_5("5","终审通过"),
        AuditState_6("6","终审不通过");

        private String code;
        private String name;

        AuditState(String code,String name) {
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
     * 活动变更详情模型变更类型枚举
     */
    public enum OperType {
        ADD("add","新增"),
        MOD("mod","修改"),
        DEL("del","删除");

        private String code;
        private String name;

        OperType(String code,String name) {
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
     * 活动变更详情模型字段类型枚举
     */
    public enum FieldType {
        FieldType_1("1","字符和数字"),
        FieldType_2("2","时间"),
        FieldType_3("3","图片");

        private String code;
        private String name;

        FieldType(String code,String name) {
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
     * 活动卖家范围和买家范围有效状态枚举
     */
    public enum Status {
        WaitAudit("0","待审核"),
        Audited("1","有效"),
        AuditFailed("-1","审核不通过");

        private String code;
        private String name;

        Status(String code,String name) {
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
     * 营销活动是否修改审批中
     */
    public enum ActivityIsModifying {
        NO("0","不在修改审批中"),
        YES("1","在修改审批中");

        private String code;
        private String name;

        ActivityIsModifying(String code,String name) {
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
     * 营销活动级别，记录活动发起的对象级别
     */
    public enum ActivityLevel {
        LEVEL_1("1","省级活动"),
        LEVEL_2("2","地市级活动"),
        LEVEL_3("3","厂商活动"),
        LEVEL_4("4","国省包商活动"),
        LEVEL_5("5","地包商活动");

        private String code;
        private String name;

        ActivityLevel(String code,String name) {
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
