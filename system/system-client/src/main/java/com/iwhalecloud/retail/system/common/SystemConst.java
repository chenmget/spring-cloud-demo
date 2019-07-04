package com.iwhalecloud.retail.system.common;

/**
 * @author zwl
 * @date 2018/11/29
 */
public class SystemConst {

    // 表对应的缓存名称，1、表名的缓存名称：表示单条的缓存的；2、非表名的缓存: 表示存非单条的数据（一般是有父子级关系的列表）
    // 用户表sys_user使用的缓存名称
    public static final String CACHE_NAME_SYS_USER = "sys_user";
    // 用户-角色表sys_user_role使用的缓存名称
    public static final String CACHE_NAME_SYS_USER_ROLE = "sys_user_role";
    // 角色-菜单表sys_role_menu使用的缓存名称
    public static final String CACHE_NAME_SYS_ROLE_MENU = "sys_role_menu";
    // 角色-菜单表sys_menu使用的缓存名称
    public static final String CACHE_NAME_SYS_MENU = "sys_menu";
    // 区域表sys_common_region使用的缓存名称
    public static final String CACHE_NAME_SYS_COMMON_REGION = "sys_common_region";
    // 区域表sys_common_region 父级的 list集合使用的缓存名称
    public static final String CACHE_NAME_SYS_COMMON_REGION_LIST = "sys_common_region_list";
    // 通用组织信息sys_common_org使用的缓存名称
    public static final String CACHE_NAME_SYS_COMMON_ORG = "sys_common_org";
    // 通用组织信息sys_common_org父级的 list集合使用的缓存名称
    public static final String CACHE_NAME_SYS_COMMON_ORG_LIST = "sys_common_org_list";
    // 配置表sys_config_info使用的缓存名称
    public static final String CACHE_NAME_SYS_CONFIG_INFO = "sys_config_info";
    // 公共字段表表sys_public_dict使用的缓存名称
    public static final String CACHE_NAME_SYS_PUBLIC_DICT = "sys_public_dict";

    // 湖南默认一级本地网的 父级区域ID
    public static final String HN_DEFAULT_PAR_REGION_ID = "10000000";

    // 湖南默认通用组织信息 父级区域ID
    public static final String HN_DEFAULT_PARENT_ORG_ID = "843000000000000";

    // 最多登录失败次数限制
    public static final int MAX_FAIL_LOGIN_COUNT = 5;

    // 用户状态 1有效  0禁用 2：无效(删除） 3:锁住（密码错误次数超限 等等）
    public static final int USER_STATUS_INVALID = 0;
    public static final int USER_STATUS_VALID = 1;
    public static final int USER_STATUS_DELETE = 2;
    public static final int USER_STATUS_LOCK = 3;

    // -------------------工号角色（沿用电商的）-------------------------
    // 用户 founder 类型
    public static final int USER_FOUNDER_0 = 0; // 电信员工(终端公司运营人员)
    public static final int USER_FOUNDER_1 = 1; // 超级管理员
    public static final int USER_FOUNDER_2 = 2; // 省管理员
    public static final int USER_FOUNDER_3 = 3; // 零售商（店中商）
    public static final int USER_FOUNDER_4 = 4; // 供货商：省包
    public static final int USER_FOUNDER_5 = 5; // 供货商：地包
    public static final int USER_FOUNDER_6 = 6; // 代理商：店员
    public static final int USER_FOUNDER_7 = 7; // 经营主体
    public static final int USER_FOUNDER_8 = 8; // 厂商
    public static final int USER_FOUNDER_9 = 9; // 地市管理员

    // 商家相关人员
    public static final int USER_FOUNDER_11 = 11; // 销售人员
    public static final int USER_FOUNDER_12 = 12; // 终端公司管理人员
    public static final int USER_FOUNDER_13 = 13; // 售后服务平台管理人员
    // 电信相关人员
    public static final int USER_FOUNDER_21 = 21; // 装维人员
    public static final int USER_FOUNDER_22 = 22; // 地市装维管理员
    public static final int USER_FOUNDER_23 = 23; // 区县装维主管
    public static final int USER_FOUNDER_24 = 24; // 省公司市场部管理人员
    public static final int USER_FOUNDER_31 = 31; // 电信客户

    // 用户关联类型  rel_type
    public static final String REL_TYPE_PARTNER = "partner"; // 分销商
    public static final String REL_TYPE_SUPPLIER = "supplier"; // 供应商

    /**
     * 公告审核流程实例id
     */
    public static final String NOTICE_AUDIT_PROCESS_ID = "9";

    /**
     * 云货架账号标识
     */
    public static final Integer USER_RESOURCE_YHJ = 1;
    /**
     * 统一门户工号标识
     */
    public static final Integer USER_SOURCE_PORTAL = 2;

    /**
     * 新增标识：A
     */
    public static final String ADD_TYPE = "A";

    /**
     * 修改标识：U
     */
    public static final String UPDATE_TYPE = "U";

    public static final String ACT_TYPE_ADD = "ADD";
    public static final String ACT_TYPE_MOD = "MOD";
    public static final Integer SUCCESS_CODE = 0;
    public static final Integer ERROR_CODE = 1;

    /**
     * 国际化配置文件里当前语言
     */
    public static final String LOCALE_CODE = "Locale";

    /**
     * 审核状态枚举
     */
    public enum UserFounderEnum {
        ADMIN_SUPER(1, "超级管理员"),
        ADMIN_ORDINARY(2, "普通管理员"),
        PARTNER(3, "零售商（店中商）"),
        SUPPLIER_PROVINCE(4, "省包供应商"),
        SUPPLIER_GROUND(5, "地包供应商"),
        PARTNER_STAFF(6, "分销商员工"),
        PARTNER_BUSINESS_ENTITY(7, "经营主体"),
        MANUFACTURER(8, "厂商");

        private String value;
        private Integer code;

        UserFounderEnum(Integer code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 审核状态枚举
     */
    public enum ValidStatusEnum {
        /**
         * 未校验
         */
        NOT_VALID(0, "未校验"),
        /**
         * 已校验
         */
        HAVE_VALID(1, "已校验");
        private String value;
        private Integer code;

        ValidStatusEnum(Integer code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 发送状态枚举
     */
    public enum SendStatusEnum {
        /**
         * 未发送
         */
        NOT_SEND(0, "未发送"),
        /**
         * 已发送
         */
        HAVE_SEND(1, "已发送");
        private String value;
        private Integer code;

        SendStatusEnum(Integer code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 通知发布类型枚举
     */
    public enum NoticePublishTypeEnum {
        SYSTEM("1", "系统类型"),
        DIRECTION("2", "定向类");
        private String value;
        private String type;

        NoticePublishTypeEnum(String type, String value) {
            this.type = type;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 通知类型枚举
     */
    public enum NoticeTypeEnum {
        BUSINESS("1", "业务类"),
        HOT_MSG("2", "热点消息"),
        NOTICE("3", "通知公告");
        private String value;
        private String type;

        NoticeTypeEnum(String type, String value) {
            this.type = type;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 通知状态枚举
     */
    public enum NoticeStatusEnum {
        VALID("1", "有效"),
        INVALID("0", "无效"),
        AUDIT("2", "待审核"),
        AUDIT_FAILED("3", "审核不通过");
        private String value;
        private String code;

        NoticeStatusEnum(String code, String value) {
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
     * 用户是否已读状态枚举
     */
    public enum NoticeUserReadStatusEnum {
        NOT_READ("0", "未读"),
        HAS_READ("1", "已读");
        private String value;
        private String code;

        NoticeUserReadStatusEnum(String code, String value) {
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
     * 用户是否已读状态枚举
     */
    public enum ObjTypeEnum {
        B2C_GOODS("1", "B2C商品"),
        B2C_STORE("2", "B2C门店"),
        B2B_GOODS("3", "B2B商品"),
        B2B_SUPPLIER("4", "B2B供应商");
        private String value;
        private String code;

        ObjTypeEnum(String code, String value) {
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
     * 用户是否已读状态枚举
     */
    public enum PlatformFlag {
        TRADE("0", "交易平台"),
        ADMIN("1", "管理平台");
        private String value;
        private String code;

        PlatformFlag(String code, String value) {
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
     * 文件类型
     */
    public enum FileType {
        IMG_FILE("1", "图片"),
        TXT_FILE("2", "文件");
        private String value;
        private String code;

        FileType(String code, String value) {
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
     * 文件业务类型
     */
    public enum FileClass {
        BUSINESS_LICENSE("1", "营业执照"),
        IDENTITY_CARD_PHOTOS("2", "身份证照片"),
        AUTHORIZATION_CERTIFICATE("3", "授权证书"),
        CONTRACT_TEXT("4", "合同文本");
        private String value;
        private String code;

        FileClass(String code, String value) {
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
     * 状态常量
     */
    public enum StatusCdEnum {
        STATUS_CD_VALD("1000", "有效"),
        STATUS_CD_INVALD("1100", "失效");

        private String code;
        private String value;

        StatusCdEnum(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 区域等级枚举
     */
    public enum REGION_LEVEL {
        LEVEL_0("0", "集团公司"),
        LEVEL_10("10", "省公司"),
        LEVEL_20("20", "虚拟本地网"),
        LEVEL_30("30", "地市公司"),
        LEVEL_40("40", "区域经营部");

        private String code;
        private String value;

        REGION_LEVEL(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 通知发布类型枚举
     */
    public enum loginTypeEnum {
        PORTAL("portal", "统一门户工号"),
        YHJ("yhj", "云货架账号");
        private String value;
        private String type;

        loginTypeEnum(String type, String value) {
            this.type = type;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 数据字典里面保存的
     * 绿色通道机型、门店权限字典
     */
    public enum GreenChannelType {
        ChainStore("1230600001", "连锁门店"),
        Echannle("1230600002", "电子渠道"),
        Honor("1230600003", "荣耀绿色通道"),
        XiaoMi("1230600004", "小米绿色通道"),
        HuaWei("1230600005", "华为绿色通道"),
        SpecialType("1230600006", "特殊机型绿色通道");
        private String value;
        private String type;

        GreenChannelType(String type, String value) {
            this.type = type;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 数据字典里面保存的
     * 绿色通道机型权类型
     */
    public enum TagType {
        mechant("MERCHANT_TAG", "门店权限"),
        product("PRODUCT_TAG", "机型权限");
        private String value;
        private String type;

        TagType(String type, String value) {
            this.type = type;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    public enum SysUserStatusCdEnum{
        STATUS_CD_INVALD(0,"禁用"),
        STATUS_CD_VALD(1,"有效"),
        STATUS_CD_DELETE(2,"删除"),
        STATUS_CD_LOCK(3,"锁住");

        private int code;
        private String value;

        SysUserStatusCdEnum(int code, String value){
            this.code = code;
            this.value = value;
        }
        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }
}
