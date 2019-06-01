package com.iwhalecloud.retail.partner.common;

/**
 * @author mzl
 * @date 2018/10/30
 */
public class PartnerConst {

    // 表对应的缓存名称，1、表名的缓存名称：表示单条的缓存的；2、非表名的缓存: 表示存非单条的数据（一般是有父子级关系的列表）
    // 商家表par_merchant使用的缓存名称
    public static final String CACHE_NAME_PAR_MERCHANT = "par_merchant";
    // 经营主体表par_business_entity使用的缓存名称
    public static final String CACHE_NAME_PAR_BUSINESS_ENTITY = "par_business_entity";

    /**
     * 国际化配置文件里当前语言
     */
    public static final String LOCALE_CODE = "Locale";

    /**
     * 返回结果正常
     */
    public static final String RESULE_CODE_SUCCESS = "0";
    /**
     * 返回结果失败
     */
    public static final  String RESULE_CODE_FAIL = "-1";
    /**
     * 限定区域为10公里
     */
    public static final Double distance = 10.00;

    /**
     * 商家id校验结果
     */
    public static final String MERCHANT_IS_NULL = "商家id为空";


    /**
     *  通用状态 1 有效、0失效
     */
    public enum CommonState {
        VALID("1","有效"),
        INVALID("0","无效");
        private String value;
        private String code;
        CommonState(String code, String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getCode() {
            return code;
        }
    }


    /**
     *  电信通用状态 1000 有效、1100失效
     */
    public enum TelecomCommonState {
        VALID("1000","有效"),
        INVALID("1100","无效");
        private String value;
        private String code;
        TelecomCommonState(String code, String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * 是否被删除
     */
    public enum IsDeleted {
        /**
         * 未删除
         */
        NOT_DELETED("0","未删除"),
        /**
         * 已删除
         */
        HAVE_DELETED("1","已删除");
        private String value;
        private String code;
        IsDeleted(String code, String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     *  状态 0 有效、1失效
     */
    public enum State {
        /**
         * 有效
         */
        EFFECTIVE("0","有效"),
        /**
         * 无效
         */
        INVALID("1","无效");
        private String value;
        private String code;
        State(String code, String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     *  商家类型
     */
    public enum MerchantTypeEnum {

        MANUFACTURER("1","厂商"),
        SUPPLIER_GROUND("2","地包供应商"),
        SUPPLIER_PROVINCE("3","省包供应商"),
        PARTNER("4","零售商");

        private String type;
        private String name;
        MerchantTypeEnum(String type, String name){
            this.type = type;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        /**
         * 根据key获取value
         *
         * @param key
         * @return String
         */
        public static String getNameByType(String key) {
            MerchantTypeEnum[] enums = MerchantTypeEnum.values();
            for (int i = 0; i < enums.length; i++) {
                if (enums[i].getType().equals(key)) {
                    return enums[i].getName();
                }
            }
            return null;
        }
    }

    /**
     *  商家状态
     */
    public enum MerchantStatusEnum {
        /**
         * 渠道状态:  有效 1000  主动暂停 1001 异常暂停 1002 无效 1100 终止 1101
         * 退出 1102 未生效 1200 已归档 1300 预退出 8922 冻结 8923
         */
        VALID("1000","有效"),
        INVALID("1100","有效"),
        PAUSE("1001","主动暂停"),
        EXCEPTION_PAUSE("1002","异常暂停"),
        TERMINATION("1101","终止"),
        QUIT("1102","退出"),
        NOT_EFFECT("1200","未生效"),
        ARCHIVED("1300","已归档"),
        PRE_QUIT("8922","预退出"),
        FREEZE("8923","冻结");

        private String code;
        private String name;
        MerchantStatusEnum(String code, String name){
            this.code = code;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return code;
        }
    }

    /**
     * 对象类型:
     * RULE_TYPE是1 经营权限时: TARGET_TYPE是： 1 品牌  2 机型  3 区域 4 商家；
     * RULE_TYPE是2 绿色通道权限时: TARGET_TYPE是： 1 产品，TARGET_ID填写PRODUCT_BASE_ID   ,  2 机型，TARGET_ID填写PRODUCT_ID ;
     * RULE_TYPE是3 调拨权限时:  TARGET_TYPE是： 2 机型 3 区域 4 商家；
     */
    /**
     *  商家权限规则类型
     */
    public enum MerchantRuleTypeEnum {
        BUSINESS("1","经营权限"),
        GREEN_CHANNEL("2","绿色通道权限"),
        TRANSFER("3","调拨权限");

        private String type;
        private String name;
        MerchantRuleTypeEnum(String type, String name){
            this.type = type;
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType() {
            return type;
        }
    }
    /**
     *  商家经营权限的 对象类型
     */
    public enum MerchantBusinessTargetTypeEnum {
        BRAND("1","品牌"),
        MODEL("2","机型"),
        REGION("3","区域"),
        MERCHANT("4","商家");

        private String type;
        private String name;
        MerchantBusinessTargetTypeEnum(String type, String name){
            this.type = type;
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType() {
            return type;
        }
    }

    /**
     *  商家绿色通道权限的 对象类型
     */
    public enum MerchantGreenChannelTargetTypeEnum {
        PRODUCT("1","产品"),
        MODEL("2","机型");

        private String type;
        private String name;
        MerchantGreenChannelTargetTypeEnum(String type, String name){
            this.type = type;
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType() {
            return type;
        }
    }

    /**
     *  商家调拨权限的 对象类型
     */
    public enum MerchantTransferTargetTypeEnum {
        MODEL("2","机型"),
        REGION("3","区域"),
        MERCHANT("4","商家");

        private String type;
        private String name;
        MerchantTransferTargetTypeEnum(String type, String name){
            this.type = type;
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType() {
            return type;
        }
    }

    /**
     *  商家账户类型
     */
    public enum MerchantAccountTypeEnum {

        BEST_PAY("1","翼支付"),
        WE_CHAT_PAY("2","微信支付"),
        ALI_PAY("3","支付宝"),
        BANK_ACCOUNT("4","银行账户");

        private String type;
        private String name;
        MerchantAccountTypeEnum(String type, String name){
            this.type = type;
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType() {
            return type;
        }
    }

    /**
     *  商家权限申请单子项 操作类型
     */
    public enum PermissionApplyItemOperationTypeEnum {

        ADD("A","添加"),
        UPDATE("U","修改"),
        DELETE("D","删除"),
        ;
        private String type;
        private String name;
        PermissionApplyItemOperationTypeEnum(String type, String name){
            this.type = type;
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType() {
            return type;
        }
    }

    /**
     *  商家权限申请单 状态类型
     */
    public enum PermissionApplyStatusEnum {

        AUDITING("1002","审核中"),
        PASS("1003","审核通过"),
        NOT_PASS("1004","审核不通过"),
        ;
        private String code;
        private String name;
        PermissionApplyStatusEnum(String code, String name){
            this.code = code;
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getCode() {
            return code;
        }
    }

    /**
     *  商家权限申请单 状态类型
     */
    public enum PermissionApplyTypeEnum {

        PERMISSION_APPLY("10","权限申请"),
        ;
        private String code;
        private String name;
        PermissionApplyTypeEnum(String code, String name){
            this.code = code;
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getCode() {
            return code;
        }
    }

    /**
     *  商家经营权限的 是否已经赋权
     */
    public enum AssignedFlgEnum {
        NO("0","否"),
        YES("1","是");

        private String type;
        private String name;
        AssignedFlgEnum(String type, String name){
            this.type = type;
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType() {
            return type;
        }
    }
}
