package com.iwhalecloud.retail.warehouse.common;

public final class MarketingResConst {
    public static final String ZOP_URL = "zop.url";
    public static final String ZOP_TIMEOUT = "zop.timeout";
    public static final String ZOP_QUERY_APPSECRET = "zop.querySecret";
    public static final String ZOP_EXCUTE_APPSECRET = "zop.excuteSecret";
    public static final String ZOP_APPSECRET = "zop.secret";

    public static final String ZOP_SUC = "00000";

    public static final String MK_RS_SUC = "0000";
    public static final String MK_EXCUTE_RS_SUC = "0";


    public static final String SERVICE_CODE = "bandout";

    public static final String PAGE_SIZE = "10";
    public static final String PAGE_INDEX = "1";


    public static final String ACT_TYPE_ADD = "A";
    public static final String ACT_TYPE_UPDATE = "U";

    public static final String TO_MK_SUC = "0000";
    public static final String TO_MK_ERROR = "9999";

    public static final int SYN_DAY = -1;

    public static final int SYN_BATCH = 500;


    public static final String COMMON_YES = "1";
    public static final String COMMON_NO = "0";

    public static final String DEF_CREATER="admin";


    public static enum ServiceEnum {
        SyncTerminal("data.markedata.SyncTerminal", "移动串码入库", "1.0"),
        EBuyTerminal("data.markedata.EBuyTerminal", "固网串码入库", "1.0"),
        SynMktInstStatus("data.markedata.SynMktInstStatus", "同步串码实例的变更信息到零售商仓库（退库）", "1.0"),
        QryStoreMktInstInfo("qry.resinfo.QryStoreMktInstInfo", "按串码查询零售商仓库终端的实例信息", "1.0"),
        QryMktInstInfoByCondition("qry.resinfo.QryMktInstInfoByCondition", "按多种条件查询零售商仓库终端的实例列表", "1.0"),
        StoreInventoryQuantity("qry.resinfo.StoreInventoryQuantity", "按条件查询零售商仓库终端的库存数量 ", "1.0"),
        OrdInventoryChange("ord.operres.OrdInventoryChange", "固网同步ITME ", "1.0"),

        synMarkResStore("qry.resinfo.SynMarkResStore", "仓库信息同步 ", "1.0");

        private String code;
        private String name;
        private String version;

        ServiceEnum(String code, String name, String version) {
            this.code = code;
            this.name = name;
            this.version = version;
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

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public static enum ResultEnum {
        FAIL("-1", "串码推送ITMS(新增)失败"),
        SUCESS("0", "串码推送ITMS成功"),
        EXISTS("1", "串码推送ITMS(新增)已经存在");

        private String code;
        private String name;

        ResultEnum(String code, String name) {
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

    public static enum ITME_METHOD {
        ADD("ITMS_ADD", "新增"),
        UPDATE("ITMS_XG", "修改"),
        DELETE("ITMS_DELL", "删除");

        private String code;
        private String name;

        ITME_METHOD(String code, String name) {
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
