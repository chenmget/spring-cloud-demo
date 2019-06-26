package com.iwhalecloud.retail.warehouse.common;

/**
 * @author Z
 * @date 2018/11/27
 */
public class ResourceConst {

    /**
     * 返回结果正常
     */
    public static final String RESULE_CODE_SUCCESS = "0";
    /**
     * 返回结果失败
     */
    public static final String RESULE_CODE_FAIL = "-1";
    /**
     * 有效
     */
    public final static String VALID = "1000";
    /**
     * 无效
     */
    public final static String IN_VALID = "1100";
    /**
     *入库
     */
    public final static String PUT_IN_STOAGE = "1100";
    /**
     * 出库
     */
    public final static String OUT_PUT_STOAGE = "1000";

    /**
     * 常量是
     */
    public final static String  CONSTANT_YES = "1";

    /**
     * 常量否
     */
    public final static String  CONSTANT_NO = "0";


    /**
     * 绿色通道流程实例ID
     */
    public final static String  GREEN_CHANNEL_WORK_FLOW_INST = "8";
    /**
     * 调拨调出方审核流程实例ID
     */
    public final static String  ALLOCATE_WORK_FLOW_INST = "7";
    /**
     * 调拨两端都要审核流程实例ID
     */
    public final static String  ALLOCATE_WORK_FLOW_INST_2 = "12";
    /**
     * 移动串码审核流程
     */
    public final static String  MOVE_NBR_WORK_FLOW_INST = "13";
    /**
     * 一步抽检流程（固网）
     */
    public final static String  ONE_STEP_WORK_FLOW_INST = "14";
    /**
     * 厂商串码入库集采流程实例（固网）
     */
    public final static String  FIXED_NBR_WORK_FLOW_INST = "15";
    /**
     * 两步抽检流程（固网）
     */
    public final static String  TWO_STEP_WORK_FLOW_INST = "16";

    /**
     * 调拨返回成功的消息
     */
    public final static String ALLOCATE_SUCESS_MSG = "串码调拨已提交";
    /**
     * 调拨返回审核中的消息
     */
    public final static String ALLOCATE_AUDITING_MSG = "串码调拨审核中";

    /**
     * 返回成功的消息
     */
    public final static String SUCESS_MSG = "非交易类串码免审核限额不足，已为您提交申请";

    /**
     * 静态参数表ID产品阈值对应ID
     */
    public final static String INVENTORY_CONFIG_ID = "INVENTORY_WARING_VALUE";

    /**
     * 仓库不存在时，但是又是分片字段，就是不能为空，约定为-1
     */
    public final static String NULL_STORE_ID = "-1";

    /**
     * 国际化配置文件里当前语言
     */
    public static final String LOCALE_CODE = "Locale";

    /**
     * 串码实列状态
     */
    public enum STATUSCD {
        // 主数据没有
        AUDITING("1301","待审核"),
        // 1202	已领用可销售
        AVAILABLE("1202","在库可用"),
        // 1210	终端调拨中
        ALLOCATIONING("1210","调拨中"),
        // 1211	终端已调拨
        ALLOCATIONED("1211","已调拨"),
        // PICKED("1202","已领用可销售"),
        RESTORAGEING("1305","退库中"),
        EXCHANGEING("1306","换货中"),
        // 1205	退换货已冻结
        RESTORAGED("1205","退换货已冻结"),
        // 1203	已销售未补贴
        SALED("1203","已销售"),
        // 1110	已作废
        DELETED("1110","已作废");

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

        public static String getName(String name){
            for (STATUSCD statusCd : STATUSCD.values()){
                if(statusCd.code.equals(name)){
                    return statusCd.name;
                }
            }
            return "";
        }
    }


    /**
     * 串码来源 1厂商，2 供应商，3零售商
     */
    public enum SOURCE_TYPE {
        // 厂商
        MERCHANT("1","厂商"),
        // 供应商
        SUPPLIER("2","供应商"),
        // 3零售商
        RETAILER("3","零售商");

        private String code;
        private String name;

        SOURCE_TYPE(String code,String name) {
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

        public static String getName(String name){
            for (SOURCE_TYPE sourceType : SOURCE_TYPE.values()){
                if(sourceType.code.equals(name)){
                    return sourceType.name;
                }
            }
            return "";
        }
    }

    /**
     * 状态常量
     */
    public enum StatusCdEnum {
        // 有效
        STATUS_CD_VALD("1000","有效"),
        // 失效
        STATUS_CD_INVALD("1100","失效");

        private String code;
        private String value;

        StatusCdEnum(String code, String value){
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
     * 变动事件状态
     */
    public enum EVENTSTATE {
        // 新建
        NEW_CREATE("1001","新建"),
        // 处理中
        PROCESSING("1002","处理中"),
        // 完成
        DONE("1003","完成"),
        // 取消
        CANCEL("1004","取消");

        private String code;
        private String name;

        EVENTSTATE(String code,String name) {
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
     * RES-C-0008
     * 变动事件-对象类型
     */
    public enum EVENT_OBJTYPE{
        // 资源申请单
        PUT_STORAGE("1001","资源申请单"),
        // 订单项
        ALLOT("1002","订单项"),
        // 产品实例
        SALE_TO_ORDER("1003","产品实例"),
        // 销售品实例
        RECEIVE("1004","销售品实例"),
        // 客户
        RECYCLE("1005","客户");

        private String code;
        private String name;

        EVENT_OBJTYPE(String code,String name) {
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
     * 事件类型
     */
    public enum EVENTTYPE {
        // 入库
        PUT_STORAGE("1001","入库"),
        // 调拨
        ALLOT("1002","调拨"),
        // 订单销售
        SALE_TO_ORDER("1003","订单销售"),
        // 领用
        RECEIVE("1004","领用"),
        // 回收
        RECYCLE("1005","回收"),
        // 回缴
        BACK_TO_PAY("1006","回缴"),
        // 作废
        CANCEL("1007","作废"),
        // 赠送礼品
        PRESENTS_GAVING("1008","礼品赠送"),
        // 返销
        BUY_BACK("1009","返销"),
        // 删除
        DELETE("1010","删除"),
        // 裸机销售
        BARE_MACHINE_SALE("1013","裸机销售"),
        // 无需记录事件
        NO_RECORD("9999","无需记录事件");

        private String code;
        private String name;

        EVENTTYPE(String code,String name) {
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
     * 资源类型
     */
    public enum MKTResInstType {

        // 交易
        TRANSACTION("1","社采"),
        // 非交易
        NONTRANSACTION("2","集采"),
        // 备机
        STANDBYMACHINE("3","备机"),
        // 省内代收
        COLLECTION_BY_PROVINCE("4","省内代收"),
        // 省内代收
        TEST_FIX_LINE("5","测试终端");

        private String code;
        private String name;

        MKTResInstType(String code,String name) {
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

        public static String getMKTResInstTypeName(String code){
            if (null == code){
                return null;
            }
            for(MKTResInstType mKTResInstType:MKTResInstType.values()){
                if(mKTResInstType.getCode().equals(code)){
                    return mKTResInstType.getName();
                }
            }
            return null;
        }
    }

    /**
     * 调用BSS3.0串码采购类型
     */
    public enum PURCHASE_TYPE {
        // 省级录入
        PURCHASE_TYPE_2("2","省级录入"),
        // 自营终端
        PURCHASE_TYPE_3("3","自营终端"),
        // 其他
        PURCHASE_TYPE_5("5","其他"),
        // 集团下发
        PURCHASE_TYPE_6("6","集团下发"),
        // 集团话费券
        PURCHASE_TYPE_7("7","集团话费券"),
        // 省话费券
        PURCHASE_TYPE_8("8","省话费券"),
        // 地市话费券
        PURCHASE_TYPE_9("9","地市话费券"),
        // 地市录入
        PURCHASE_TYPE_10("10","地市录入"),
        // 集采
        PURCHASE_TYPE_11("11","集采"),
        // 社采
        PURCHASE_TYPE_12("12","社采"),
        // 省内代收
        PURCHASE_TYPE_13("13","省内代收");

        private String code;
        private String name;

        PURCHASE_TYPE(String code,String name) {
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

        public static String getPurchaseTypeName(String code){
            if (null == code){
                return null;
            }
            for(PURCHASE_TYPE purchaseType:PURCHASE_TYPE.values()){
                if(purchaseType.getCode().equals(code)){
                    return purchaseType.getName();
                }
            }
            return null;
        }
    }


    /**
     * 营销资源申请单状态
     */
    public enum MKTRESSTATE {
        // 新建
        NEW_CREATE("1001","新建"),
        // 处理中
        PROCESSING("1002","处理中"),
        // 审核通过
        REVIEWED("1005","审核通过"),
        // 完成
        DONE("1003","完成"),
        // 取消
        CANCEL("1004","取消"),
        //待抽检
        WAIT_SPOTCHECK("1006","待抽检"),
        //待抽检
        WAIT_SPOTCHECK_CUSTSUP("1007","待抽检-客支中心"),
        //抽检不通过
        WAIT_SPOTCHECK_MOBINT("1008","待抽检-移互部"),
        //待审核
        WATI_REVIEW("1009","待审核");


        private String code;
        private String name;

        MKTRESSTATE(String code,String name) {
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

        public static String getMktResStateName(String code){
            if (null == code){
                return null;
            }
            for(MKTRESSTATE mktResState:MKTRESSTATE.values()){
                if(mktResState.getCode().equals(code)){
                    return mktResState.getName();
                }
            }
            return null;
        }
    }

    /**
     * 申请单类型
     */
    public enum REQTYPE{
        // 入库申请
        PUTSTORAGE_APPLYFOR("1001","入库申请"),
        // 调拨申请
        ALLOCATE_APPLYFOR("1002","调拨申请"),
        // 退库申请
        BACKSTORAGE_APPLYFOR("1003","退库申请");

        private String code;
        private String name;

        REQTYPE(String code,String name) {
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

        public static String getReqTypeName(String code){
            if (null == code){
                return null;
            }
            for(REQTYPE reqtype:REQTYPE.values()){
                if(reqtype.getCode().equals(code)){
                    return reqtype.getName();
                }
            }
            return null;
        }
    }

    /**
     * 仓库类型
     */
    public enum STORE_SUB_TYPE{
        // 终端库
        STORE_TYPE_TERMINAL("1300","终端库");

        private String code;
        private String name;

        STORE_SUB_TYPE(String code,String name) {
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

        public static String getStoreSubTypeName(String name){
            for (STORE_SUB_TYPE storeSubType : STORE_SUB_TYPE.values()){
                if(storeSubType.code.equals(name)){
                    return storeSubType.name;
                }
            }
            return "";
        }

    }

    /**
     * 事件类型
     */
    public enum EVENT_TYPE{
        // 入库申请单
        EVENT_TYPE_1001("1001","入库申请单"),
        // 调拨申请单
        EVENT_TYPE_1002("1002","调拨申请单"),
        // 订单销售申请单
        EVENT_TYPE_1003("1003","订单销售申请单"),
        // 领用申请单
        EVENT_TYPE_1004("1004","领用申请单"),
        // 回收申请单
        EVENT_TYPE_1005("1005","回收申请单");
        private String code;
        private String name;

        EVENT_TYPE(String code,String name) {
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
     * 入库类型，
     * 1000交易入库、1001调拨入库、1002领用入库、1003绿色通道
     *
     */
    public enum STORAGETYPE {
        // 交易入库
        TRANSACTION_WAREHOUSING("1000","交易入库"),
        // 调拨入库
        ALLOCATION_AND_WAREHOUSING("1001","调拨入库"),
        // 领用入库
        LEADING_INTO_STORAGE("1002","领用入库"),
        // 绿色通道
        GREEN_CHANNEL("1003","绿色通道"),
        // 手工录入
        MANUAL_ENTRY("1004","手工录入"),
        // 管理员录入
        ADMINISTRATOR_INPUT("100401","管理员录入"),
        // 厂商录入
        VENDOR_INPUT("100402","厂商录入"),
        // 供应商录入
        SUPPLIER_INPUT("100403","供应商录入"),
        // 管理员修改
        ADMINISTRATOR_MODIFICATION("100411","管理员修改"),
        // 厂商修改
        VENDOR_MODIFICATION("100412","厂商修改"),
        // 供应商修改
        SUPPLIER_MODIFICATION("100413","供应商修改"),
        // 供应商删除
        SUPPLIER_DELETION("100433","供应商删除"),
        // 零售商删除
        RETAILER_DELETION("100434","零售商删除"),
        // 供应商删除
        SUPPLIER_RESET("100435","供应商还原"),
        // 供应商退货中
        RETURNS("1033","供应商退货中"),
        // 供应商换货中
        IN_EXCHANGE("1030","供应商换货中"),
        // 调拨中
        WAREHOUSING("1034","调拨中"),
        // 供应商发货
        SUPPLIER_DELIVERY("1035","供应商发货"),
        // 供应商收货
        SUPPLIER_RECEIVING("1036","供应商收货"),
        // 供应商退货发货
        SUPPLIER_RETURN_DELIVERY("1037","供应商退货发货"),
        // 供应商退货收货
        SUPPLIER_RETURN_AND_RECEIVE("1038","供应商退货收货"),
        // 零售商领用
        RETAILER_APPOINTMENT("1043","零售商领用"),
        // 零售商调拨
        RETAILER_ALLOCATION("1044","零售商调拨"),
        // 调拨入库取消
        ALLOCATION_AND_WAREHOUSING_CANCEL("100101","调拨入库取消");

        private String code;
        private String name;

        STORAGETYPE(String code,String name) {
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

        public static String getStorageTypeName(String name){
            for (STORAGETYPE storageType : STORAGETYPE.values()){
                if(storageType.code.equals(name)){
                    return storageType.name;
                }
            }
            return "";
        }
    }

    /**
     * 申请单查询类型
     */
    public enum REQUEST_QRY_TYPE{
        // 调入的
        REQUEST_QRY_TYPE_IN("in","调入的"),
        // 调出的
        REQUEST_QRY_TYPE_OUT("out","调出的");

        private String code;
        private String name;

        REQUEST_QRY_TYPE(String code,String name) {
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
     * 调拨审核类型
     */
    public enum ALLOCATE_AUDIT_TYPE{
        // 调入的
        ALLOCATE_AUDIT_TYPE_0("0","不能调拨"),
        // 调出的
        ALLOCATE_AUDIT_TYPE_1("1","不审核"),

        ALLOCATE_AUDIT_TYPE_2("2","调出方和调入方都审核");


        private String code;
        private String name;

        ALLOCATE_AUDIT_TYPE(String code,String name) {
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
     * 零售商CRM状态枚举
     */
    public enum CRM_STATUS{
        CRM_STATUS_AVAIBLE("1000","可用"),

        CRM_STATUS_SALED_SUBSIDY("1203","已售(未补贴)"),

        CRM_STATUS_SILENT("1204","沉默"),

        CRM_STATUS_SCRAP("1205","已报损作废"),

        CRM_STATUS_SALED_NO_SUBSIDY("1207","已销售已补贴"),

        CRM_STATUS_REJECTED("1228","退货"),

        CRM_STATUS_EXPIRE("1238","已过期"),

        CRM_STATUS_UNCLAIMED("1249","已下发待认领");

        private String code;
        private String name;

        CRM_STATUS(String code,String name) {
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

        public static String getCrmStatusName(String name){
            for (CRM_STATUS crmStatus : CRM_STATUS.values()){
                if(crmStatus.code.equals(name)){
                    return crmStatus.name;
                }
            }
            return "";
        }
    }

    /**
     * 仓库大类
     */
    public enum STORE_TYPE{
        // 终端库
        PROVINCE("1000","省级库"),
        CITY("1100","本地网库");

        private String code;
        private String name;

        STORE_TYPE(String code,String name) {
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

        public static String getStoreTypeName(String name){
            for (STORE_SUB_TYPE storeSubType : STORE_SUB_TYPE.values()){
                if(storeSubType.code.equals(name)){
                    return storeSubType.name;
                }
            }
            return "";
        }
    }

    /**
     * 仓库大类
     */
    public enum STORE_GRADE{
        // 终端库
        PROVINCE("1000","省级库"),
        CITY("1100","本地网库");

        private String code;
        private String name;

        STORE_GRADE(String code,String name) {
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

        public static String getStoreGradeName(String name){
            for (STORE_SUB_TYPE storeSubType : STORE_SUB_TYPE.values()){
                if(storeSubType.code.equals(name)){
                    return storeSubType.name;
                }
            }
            return "";
        }
    }

    /**
     * 申请单详情状态
     */
    public enum DetailStatusCd{
        STATUS_CD_1002("1002","处理中"),
        STATUS_CD_1003("1003","完成"),
        STATUS_CD_1004("1004","审核不通过"),
        STATUS_CD_1005("1005","审核通过"),
        STATUS_CD_1009("1009","待审核");
        private String code;
        private String name;

        DetailStatusCd(String code,String name) {
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

        public static String getNameByCode(String code) {
            DetailStatusCd[] enums = values();
            for (DetailStatusCd obj : enums) {
                if (obj.code.equals(code)) {
                    return obj.name;
                }
            }
            return null;
        }
        public static String getCodeByName(String name) {
            DetailStatusCd[] enums = values();
            for (DetailStatusCd obj : enums) {
                if (obj.name.equals(name)) {
                    return obj.code;
                }
            }
            return null;
        }
    }

    /**
     * 集采类型
     */
    public enum IS_GOVORJC{
        IS_GOVORJC_1("1","政企"),
        IS_GOVORJC_2("2","集采");
        private String code;
        private String name;

        IS_GOVORJC(String code,String name) {
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
     * 申请单详情isFixLine字典值
     */
    public enum CheckType {
        CheckType_1("1","技术抽检"),

        CheckType_2("2","泛智能抽检");

        private String code;
        private String name;

        CheckType(String code,String name) {
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
