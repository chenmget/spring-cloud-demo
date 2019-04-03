package com.iwhalecloud.retail.workflow.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.TaskDTO;
import com.iwhalecloud.retail.workflow.dto.req.*;
import com.iwhalecloud.retail.workflow.dto.resp.DealTaskDetailGetResp;
import com.iwhalecloud.retail.workflow.dto.resp.HandleTaskDetailGetResp;
import com.iwhalecloud.retail.workflow.dto.resp.HandleTaskPageResp;
import com.iwhalecloud.retail.workflow.dto.resp.TaskPageResp;

import java.util.List;

public interface TaskService{

    /**
     * 启动工作流
     * @param processStartDTO
     * @return
     */
    ResultVO startProcess(ProcessStartReq processStartDTO);

    /**
     * 创建工单
     * @param taskAddReq
     * @return
     */
    ResultVO addWorkTask(WorkTaskAddReq taskAddReq);

    /**
     * 领取任务（流程使用）
     * @return
     */
    ResultVO receiveTask(FlowTaskClaimReq taskClaimDTO);

    /**
     * 流程下一步
     * @param routeNextDTO
     * @return
     */
    ResultVO nextRoute(RouteNextReq routeNextDTO);

    /**
     * 处理工单
     * @param workTaskHandleReq
     * @return
     */
    ResultVO handleWorkTask(WorkTaskHandleReq workTaskHandleReq);

    /**
     * 我的待办查询
     * @param req 待办查询请求对象
     * @return
     */
    ResultVO<Page<TaskPageResp>> queryTaskPage(TaskPageReq req);

    /**
     * 查询我的待办总数<br>
     *     我的申请单根据createUserId字段查询<br>
     *     待处理根据handlerUserId字段查询<br>
     * @param req 待办查询请求对象
     * @return
     */
    ResultVO<Long> queryTaskCnt(TaskPageReq req);

    /**
     * 查询我的经办
     * @param req 经办查询请求对象
     * @return
     */
    ResultVO<Page<HandleTaskPageResp>> queryHandleTaskPage(HandleTaskPageReq req);


    /**
     * 查询我的经办总数<br>
     * @param req 经办查询请求对象
     * @return
     */
    ResultVO<Long> queryHandleTaskCnt(HandleTaskPageReq req);

    /**
     * 待办处理详情请求
     * @param req
     * @return
     */
    ResultVO<DealTaskDetailGetResp> dealTaskDetail(TaskDealDetailReq req);

    /**
     * 经办详情请求
     * @param req
     * @return
     */
    ResultVO<HandleTaskDetailGetResp> getHandleTaskDetail(HandleTaskDetailReq req);

    /**
     * 执行下一步和领取任务
     * @param req
     * @return
     */
    ResultVO nextRouteAndReceiveTask(NextRouteAndReceiveTaskReq req);

    /**
     * 获取下一环节可处理人
     * @param nextNodeId
     * @return
     */
    ResultVO queryNextNodeRights(String nextNodeId,String taskId);

    /**
     * 获取待处理的任务
     * @param formId 业务ID
     * @return
     */
    ResultVO<List<TaskDTO>> getTaskByFormId(String formId);

    /**
     *
     * @param taskId
     * @return
     */
    ResultVO<TaskDTO> getTaskById(String taskId);

}