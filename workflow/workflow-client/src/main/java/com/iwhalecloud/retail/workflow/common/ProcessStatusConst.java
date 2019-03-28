package com.iwhalecloud.retail.workflow.common;

public class ProcessStatusConst {

    /**
     * 流程处理中
     */
    public static final String PROCESS_HANDLING = "1";

    /**
     * 流程已完成
     */
    public static final String PROCESS_FINISHED = "2";

    /**
     * 环节未接收
     */
    public static final String ROUTE_UNRECEIVED = "1";

    /**
     * 环节待处理
     */
    public static final String ROUTE_UNHANDLED = "2";

    /**
     * 环节未完成
     */
    public static final String ROUTE_FINISHED = "3";
}
