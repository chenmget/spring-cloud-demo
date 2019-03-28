package com.iwhalecloud.retail.workflow.common;

import com.iwhalecloud.retail.dto.ResultCode;

/**
 * 返回值枚举类
 *
 * @author mzl
 * @date 2018/9/30
 */
public enum ResultCodeEnum implements ResultCode {

    SUCCESS("0","SUCCESS"),
    ERROR("-1","系统异常"),
    FLOW_IS_EMPTY("10000","流程为空"),
    ROUTE_NOT_EQUAL_ONE("10001","没有查询到路由信息或获取到多条路由信息"),
    NOTE_RIGHTS_IS_EMPTY("10002","当前环节的用户列表为空"),
    TASK_ITEM_HANDLER_USER_DIFFER("10003","任务项处理人不一致"),
    TASK_ITEM_IS_EMPTY("10004","任务项不存在"),
    TASK_ITEM_IS_HANDLED("10005","任务项已经处理"),
    TASK_ITEM_STATE_NOT_IS_WAITING("10006","任务不是待领取"),
    USER_NOT_IN_TASK_POOL("10007","用户不存在任务池列表中"),
    TASK_POOL_IS_EMPTY("10008","任务池列表为空"),
    NEXT_ROUTE_IS_EMPTY("1009","获取下一步路由为空"),
    TASK_LIST_IS_ERROR("10010","查询待处理工单异常"),
    SYSTEM_ERROR("500","服务器内部错误"),
    PAGE_NOT_FOUND("404","页面找不到");

    private final String code;
    private final String desc;


    ResultCodeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode(){
        return code;
    }

    @Override
    public String getDesc(){
        return desc;
    }

}
