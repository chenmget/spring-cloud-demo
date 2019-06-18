package com.iwhalecloud.retail.workflow.common;

/**
 * @author mzl
 * @date 2018/10/30
 */
public class WorkFlowConst {

    // 表对应的缓存名称，1、表名的缓存名称：表示单条的缓存的；2、非表名的缓存: 表示存非单条的数据（一般是有父子级关系的列表）
    // 环节表wf_node使用的缓存名称
    public static final String CACHE_NAME_WF_NODE = "wf_node";

    // 环节权限表wf_node_rights使用的缓存名称
    public static final String CACHE_NAME_WF_NODE_RIGHTS = "wf_node_rights";

    // 路由表wf_route使用的缓存名称
    public static final String CACHE_NAME_WF_ROUTE = "wf_route";

    // 路由服务表wf_route_service使用的缓存名称
    public static final String CACHE_NAME_WF_ROUTE_SERVICE = "wf_route_service";

    // 服务表wf_service使用的缓存名称
    public static final String CACHE_NAME_WF_SERVICE = "wf_service";

    /**
     * 关联类型
     */
    public static final String RelType = "RECOMMEND";

    /**
     * 执行节点
     */
    public static final String NODE_TYPE_1="1";
    /**
     * 执行判断节点
     */
    public static final String NODE_TYPE_2="2";

    /**
     * 系统来源
     */
    public static final String SourceFrom = "MM";

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

    public static final String  WORK_TYPE = "-1";


    //*******************任务单类型**********************
    /**
     * 任务单类型：流程
     */
    public static final String TASK_TYPE_WORK_FLOW = "1";

    /**
     * 任务单类型:工单
     */
    public static final String TASK_TYPE_FORM_WORK = "2";


    //*******************任务单状态**********************
    /**任务单状态：处理中*/
    public static final String TASK_STATUS_PROCESSING = "1";
    /**任务单状态：办结*/
    public static final String TASK_STATUS_FINISH = "2";


    //******************任务项状态***********************
    /**任务项状态：待领取*/
    public static final String TASK_ITEM_STATE_WAITING = "1";
    /**任务项状态：待处理*/
    public static final String TASK_ITEM_STATE_PENDING = "2";
    /**任务项状态：已处理*/
    public static final String TASK_ITEM_STATE_FINISH = "3";

    /**
     * 国际化配置文件里当前语言
     */
    public static final String LOCALE_CODE = "Locale";

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
     *  状态 1 处理中、2 办结
     */
    public enum TaskState {
        /**
         * 处理中
         */
        HANDING("1","处理中"),
        /**
         * 办结
         */
        FINISH("2","办结");
        private String value;
        private String code;
        TaskState(String code, String value){
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
     *  状态 1待领取、2待处理 3已处理
     */
    public enum TaskItemState {
        /**
         * 待领取
         */
        WAITING("1","待领取"),
        /**
         * 待处理
         */
        PENDING("2","待处理"),
        /**
         * 已处理
         */
        HANDLED("3","已处理");
        private String value;
        private String code;
        TaskItemState(String code, String value){
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
     *  状态 1 流程、2 工单
     */
    public enum TaskType {
        /**
         * 流程
         */
        FLOW("1","流程"),
        /**
         * 工单
         */
        WORK("2","工单");
        private String value;
        private String code;
        TaskType(String code, String value){
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
     * 流程节点
     */
    public enum WF_NODE {
        NODE_START("0","开始"),
        NODE_END("1","结束");

        private String id;
        private String name;
        WF_NODE(String id,String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 流程节点
     */
    public enum RightsType {
        ApplyUser("0","申请用户"),
        Role("1","角色"),
        User("2","指定用户"),
        Dept("3","机构"),
        RoleDept("4","角色和机构"),
        RoleUserDept("5","角色切与申请用户同部门"),
        UserDept("6","申请用户部门"),
        RemoteService("7","远程服务获取");

        private String id;
        private String name;
        RightsType(String id,String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public enum TASK_SUB_TYPE {
        TASK_SUB_TYPE_1010("1","1010","串码调拨调流程（调出方要审核）"),
        TASK_SUB_TYPE_1020("1","1020","录入移动串码审批流程"),
        TASK_SUB_TYPE_1030("1","1030","供应商库存管理流程"),
        TASK_SUB_TYPE_1040("1","1040","集采终端调货流程"),
        TASK_SUB_TYPE_1050("1","1050","地包商调货流程"),
        TASK_SUB_TYPE_1060("1","1060","零售商调货流程"),
        TASK_SUB_TYPE_1070("1","1070","零售商标签管理流程"),
        TASK_SUB_TYPE_1080("1","1080","零售商非交易类串码限额申请流程"),
        TASK_SUB_TYPE_1090("1","1090","绿色通道串码入库流程"),
        TASK_SUB_TYPE_1100("1","1100","装维人员补录串码入库流程"),
        TASK_SUB_TYPE_1110("1","1110","厂家产品管理流程"),
        TASK_SUB_TYPE_1120("1","1120","供应商经营权限管理流程"),
        TASK_SUB_TYPE_1130("1","1130","供应商商品上下架管理流程"),
        TASK_SUB_TYPE_1140("1","1140","营销活动配置流程"),
        TASK_SUB_TYPE_1150("1","1150","前置补贴管理流程"),
        TASK_SUB_TYPE_1160("1","1160","销售补贴管理流程"),
        TASK_SUB_TYPE_1170("1","1170","促销资源流程"),
        TASK_SUB_TYPE_1180("1","1180","价保流程"),
        TASK_SUB_TYPE_1190("1","1190","2B退货流程"),
        TASK_SUB_TYPE_1200("1","1200","移动终端换机流程"),
        TASK_SUB_TYPE_1210("1","1210","2B移动终端换机流程"),
        TASK_SUB_TYPE_1220("1","1220","固网终端备机入库流程"),
        TASK_SUB_TYPE_1230("1","1230","固网备机下发流程"),
        TASK_SUB_TYPE_1240("1","1240","2C固网售后置换流程"),
        TASK_SUB_TYPE_1250("1","1250","2C固网售后维修流程"),
        TASK_SUB_TYPE_1260("1","1260","售后返厂流程"),
        TASK_SUB_TYPE_2010("1","2010","2B订单销售"),
        TASK_SUB_TYPE_2020("1","2020","专票信息流程"),
        TASK_SUB_TYPE_2030("1","2030","前置补贴补录流程"),
        TASK_SUB_TYPE_2040("1","2040","串码调拨流程（调出方、调入方都要审核）"),
        TASK_SUB_TYPE_2050("1","2050","通知公告审核流程"),
        TASK_SUB_TYPE_2060("1","2060","商家权限申请审核流程"),
        TASK_SUB_TYPE_3010("1","3010","录入固网串码审批流程"),
        TASK_SUB_TYPE_3020("1","3020","采购申请单审核流程"),
        TASK_SUB_TYPE_3040("1","3040","采购申请单审核流程"),
        TASK_SUB_TYPE_3030("1","3030","采购申审核流程"),
        TASK_SUB_TYPE_9504("1","9504","政企价格修改审核流程"),
        TASK_SUB_TYPE_9604("1","9604","移动终端政企价格修改审核流程"),
        TASK_SUB_TYPE_9605("1","9605","固网终端政企价格修改审核流程"),
        TASK_SUB_TYPE_3040301("1","3040301","地包商管理"),
        TASK_SUB_TYPE_3040501("1","3040501","厂商管理"),
        TASK_SUB_TYPE_3040601("1","3040601","国/省包管理3"),
        TASK_SUB_TYPE_1142("1","1142","营销活动变更流程");


        private String taskType;
        private String taskSubType;
        private String taskSubName;

        TASK_SUB_TYPE(String taskType,String taskSubType,String taskSubName) {
            this.taskType = taskType;
            this.taskSubType = taskSubType;
            this.taskSubName = taskSubName;
        }

        public String getTaskType() {
            return taskType;
        }

        public String getTaskSubType() {
            return taskSubType;
        }

        public String getTaskSubName() {
            return taskSubName;
        }
    }

    /**
     * 流程节点
     */
    public enum TASK_PARAMS_TYPE {
        NO_PARAMS(-1, "无参数"),
        JSON_PARAMS(1, "json"),
        STRING_PARAMS(2, "字符串");

        private Integer code;
        private String name;

        TASK_PARAMS_TYPE(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 流程process_id
     */
    public enum PROCESS_ID {
        PROCESS_1007("1007","调拨调出方审核流程实例"),
        PROCESS_1008("1008","绿色通道流程实例"),
        PROCESS_1012("1012","调拨两端都要审核流程实例"),
        PROCESS_1013("1013","移动串码审核流程"),
        PROCESS_1014("1014","厂商录入固网、泛智能终端串码审核流程（两步审核）"),
        PROCESS_1015("1015","厂商串码入库集采流程实例（固网）（一步审核）"),
        PROCESS_1016("1016","厂商录入固网社采(仅指机顶盒、三合一终端）串码审核流程（三步审核）");

        private String typeCode;
        private String typeName;

        PROCESS_ID(String typeCode, String typeName) {
            this.typeCode = typeCode;
            this.typeName = typeName;
        }

        public String getTypeCode() {
            return typeCode;
        }

        public String getTypeName() {
            return typeName;
        }
    }
}
