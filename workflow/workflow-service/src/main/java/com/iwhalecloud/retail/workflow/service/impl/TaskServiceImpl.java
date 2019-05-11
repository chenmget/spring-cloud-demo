package com.iwhalecloud.retail.workflow.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.common.ResultCodeEnum;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.*;
import com.iwhalecloud.retail.workflow.dto.resp.DealTaskDetailGetResp;
import com.iwhalecloud.retail.workflow.dto.resp.HandleTaskDetailGetResp;
import com.iwhalecloud.retail.workflow.dto.resp.HandleTaskPageResp;
import com.iwhalecloud.retail.workflow.dto.resp.TaskPageResp;
import com.iwhalecloud.retail.workflow.entity.Node;
import com.iwhalecloud.retail.workflow.entity.Route;
import com.iwhalecloud.retail.workflow.entity.Task;
import com.iwhalecloud.retail.workflow.entity.TaskItem;
import com.iwhalecloud.retail.workflow.manager.NodeManager;
import com.iwhalecloud.retail.workflow.manager.RouteManager;
import com.iwhalecloud.retail.workflow.manager.TaskItemManager;
import com.iwhalecloud.retail.workflow.manager.TaskManager;
import com.iwhalecloud.retail.workflow.model.TaskItemDetailModel;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @author z
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskManager taskManager;

    @Autowired
    private TaskItemManager taskItemManager;

    @Resource
    private RouteManager routeManager;

    @Resource
    private NodeManager nodeManager;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO startProcess(ProcessStartReq processStartDTO) {
        log.info("TaskServiceImpl.startProcess processStartDTO={}",JSON.toJSONString(processStartDTO));
        return taskManager.startProcess(processStartDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO receiveTask(FlowTaskClaimReq taskClaimDTO) {
        log.info("TaskServiceImpl.receiveTask taskClaimDTO={}",JSON.toJSONString(taskClaimDTO));
        return taskManager.receiveTask(taskClaimDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO nextRoute(RouteNextReq routeNextDTO) {
        log.info("TaskServiceImpl.nextRoute routeNextDTO={}",JSON.toJSONString(routeNextDTO));
        return taskManager.nextRoute(routeNextDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addWorkTask(WorkTaskAddReq taskAddReq) {
        log.info("TaskServiceImpl.addWorkTask taskAddReq={}",JSON.toJSONString(taskAddReq));
        return taskManager.addWorkTask(taskAddReq);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO handleWorkTask(WorkTaskHandleReq workTaskHandleReq) {
        log.info("TaskServiceImpl.handleWorkTask workTaskHandleReq={}",JSON.toJSONString(workTaskHandleReq));
        return taskManager.handleWorkTask(workTaskHandleReq);
    }

    @Override
    public ResultVO<Page<TaskPageResp>> queryTaskPage(TaskPageReq req) {

        log.info("TaskServiceImpl.queryTaskPage  req={}" + JSON.toJSONString(req));
        try {
            Page<TaskPageResp> page = taskManager.queryTaskPage(req);

            log.info("TaskServiceImpl.queryTaskPage  page={}" + JSON.toJSONString(page));
            return ResultVO.success(page);
        } catch (Exception e) {
            log.error("TaskServiceImpl.queryTaskPage 异常：", e);

            return ResultVO.error(e.getMessage());
        }
    }

    @Override
    public ResultVO<Long> queryTaskCnt(TaskPageReq req) {

        log.info("TaskServiceImpl.queryTaskCnt  req={}" + JSON.toJSONString(req));
        try {
            Long taskCnt = taskManager.queryTaskCnt(req);

            log.info("TaskServiceImpl.queryTaskCnt  taskCnt={}" + taskCnt);
            return ResultVO.success(taskCnt);
        } catch (Exception e) {
            log.error("TaskServiceImpl.queryTaskCnt 异常：",e);

            return ResultVO.error(e.getMessage());
        }
    }

    @Override
    public ResultVO<Long> queryHandleTaskCnt(HandleTaskPageReq req) {

        log.info("TaskServiceImpl.queryHandleTaskCnt  req={}" + JSON.toJSONString(req));
        try {
            Long taskHandleCnt = taskManager.queryHandleTaskCnt(req);

            log.info("TaskServiceImpl.queryHandleTaskCnt  taskHandleCnt={}" + taskHandleCnt);
            return ResultVO.success(taskHandleCnt);
        } catch (Exception e) {
            log.error("TaskServiceImpl.queryHandleTaskCnt 异常：",e);

            return ResultVO.error(e.getMessage());
        }
    }

    @Override
    public ResultVO<Page<HandleTaskPageResp>> queryHandleTaskPage(HandleTaskPageReq req) {

        log.info("TaskServiceImpl.queryHandleTaskPage  req={}" + JSON.toJSONString(req));

        Page<HandleTaskPageResp> page = taskManager.queryHandleTask(req);

        log.info("TaskServiceImpl.queryHandleTaskPage  page={}" + JSON.toJSONString(page));
        return ResultVO.success(page);

    }



    @Override
    public ResultVO<DealTaskDetailGetResp> dealTaskDetail(TaskDealDetailReq req) {
        //1、获取待办项基本信息
        TaskItemDetailModel taskDetailModel = taskManager.getTaskItemDetail(req.getTaskId(),req.getTaskItemId());

        log.info("TaskServiceImpl.getTaskDetail taskDetailModel={}" + JSON.toJSONString(taskDetailModel));
        if (taskDetailModel == null) {
            log.warn("TaskServiceImpl.getTaskDetail taskDetailModel records do not exist,taskItemId = {}",req.getTaskItemId());
            return ResultVO.error("记录不存在，待办项已处理");
        }

        //2、如果待办未领取，则进行领取
        if (WorkFlowConst.TaskItemState.WAITING.getCode().equals(taskDetailModel.getItemStatus())) {
            FlowTaskClaimReq flowTaskClaimReq = new FlowTaskClaimReq();
            BeanUtils.copyProperties(req, flowTaskClaimReq);
            ResultVO receiveTaskVO = this.receiveTask(flowTaskClaimReq);
            if (!receiveTaskVO.isSuccess()) {
                log.info("TaskServiceImpl.getTaskDetail receiveTaskVO={}" + JSON.toJSONString(receiveTaskVO));
                return receiveTaskVO;
            }
            //待办领取成功，将查询出来的任务项改为待处理
            taskDetailModel.setItemStatus(WorkFlowConst.TaskItemState.PENDING.getCode());
        }

        //3、获取审核记录
        DealTaskDetailGetResp resp = new DealTaskDetailGetResp();
        BeanUtils.copyProperties(taskDetailModel, resp);
        List<TaskItem> taskItems =  taskItemManager.queryTaskItem(taskDetailModel.getTaskId());
        if (taskItems != null) {
            List<DealTaskDetailGetResp.TaskItemInfo> taskItemInfos = new ArrayList<DealTaskDetailGetResp.TaskItemInfo>();
            for (TaskItem taskItem : taskItems) {
                DealTaskDetailGetResp.TaskItemInfo taskItemInfo = new DealTaskDetailGetResp.TaskItemInfo();
                BeanUtils.copyProperties(taskItem, taskItemInfo);
                taskItemInfos.add(taskItemInfo);
            }
            resp.setTaskItemInfos(taskItemInfos);
            log.info("TaskServiceImpl.getTaskDetail taskItemInfos={}" + JSON.toJSONString(taskItemInfos));
        }

        //4、获取下一个环节记录
        List<Route> routes = routeManager.listRoute(taskDetailModel.getProcessId(), taskDetailModel.getCurNodeId());
        List<DealTaskDetailGetResp.RouteInfo> routeInfos = new ArrayList<DealTaskDetailGetResp.RouteInfo>();
        if (routeInfos != null) {
            for (Route route : routes) {
                DealTaskDetailGetResp.RouteInfo routeInfo = new DealTaskDetailGetResp.RouteInfo();
                BeanUtils.copyProperties(route, routeInfo);
                Node node = nodeManager.getNode(route.getNextNodeId());
                routeInfo.setBooAppointDealUser(node.getBooAppointDealUser());
                routeInfos.add(routeInfo);
            }
            resp.setRouteInfos(routeInfos);
            log.info("TaskServiceImpl.getTaskDetail routeInfos={}" + JSON.toJSONString(routeInfos));
        }

        //5、设置当前环节是否允许编辑
        Node node = nodeManager.getNode(taskDetailModel.getCurNodeId());
        log.info("TaskServiceImpl.queryHandleTaskPage  getNode={}" + JSON.toJSONString(node));
        if (node != null) {
            resp.setBooEdit(node.getBooEdit());
        }

        log.info("TaskServiceImpl.queryHandleTaskPage  resp={}" + JSON.toJSONString(resp));
        return ResultVO.success(resp);
    }

    @Override
    public ResultVO<HandleTaskDetailGetResp> getHandleTaskDetail(HandleTaskDetailReq req) {

        Task taskEntity = taskManager.getTask(req.getTaskId());
        log.info("TaskServiceImpl.getHandleTaskDetail taskEntity={}" + JSON.toJSONString(taskEntity));
        if (taskEntity == null) {
            log.warn("TaskServiceImpl.getTaskDetail taskEntity records do not exist,taskId = {}",req.getTaskId());
            return ResultVO.error("记录不存在，待办项已处理");
        }

        //1、获取基本信息
        HandleTaskDetailGetResp resp = new HandleTaskDetailGetResp();
        BeanUtils.copyProperties(taskEntity, resp);

        //2、获取审核记录
        List<TaskItem> taskItems =  taskItemManager.queryTaskItem(req.getTaskId());
        if (taskItems != null) {
            List<HandleTaskDetailGetResp.TaskItemInfo> taskItemInfos = new ArrayList<HandleTaskDetailGetResp.TaskItemInfo>();
            for (TaskItem taskItem : taskItems) {
                HandleTaskDetailGetResp.TaskItemInfo taskItemInfo = new HandleTaskDetailGetResp.TaskItemInfo();
                BeanUtils.copyProperties(taskItem, taskItemInfo);
                taskItemInfos.add(taskItemInfo);
            }
            resp.setTaskItemInfos(taskItemInfos);
            log.info("TaskServiceImpl.getTaskDetail taskItemInfos={}" + JSON.toJSONString(taskItemInfos));
        }
        return ResultVO.success(resp);
    }

    private RouteNextReq getNextRoute(String formId) {
        // 根据formId查询processId
        List<Task> taskList = taskManager.getTaskByFormId(formId);
        if (CollectionUtils.isEmpty(taskList) || taskList.size() > 1) {
            log.info("TaskServiceImpl.getNextRoute formId ={},taskList={}" + formId, JSON.toJSONString(taskList));
            return null;
        }
        Task task = taskList.get(0);
        String processId = task.getProcessId();
        // 根据formId查询待处理任务项
        TaskItem taskItem = taskItemManager.queryWaitHandlerTaskItem(task.getTaskId());
        if (taskItem == null) {
            log.info("TaskServiceImpl.getNextRoute formId ={},taskItem={}" + formId, JSON.toJSONString(taskItem));
            return null;
        }
        RouteNextReq routeNextReq = new RouteNextReq();
        routeNextReq.setTaskItemId(taskItem.getTaskItemId());
        Route condition = new Route();
        condition.setProcessId(processId);
        condition.setCurNodeId(taskItem.getCurNodeId());
        // 查询流程下一步路由id
        List<Route> routeList = routeManager.listRouteByCondition(condition);
        if (CollectionUtils.isEmpty(routeList) || routeList.size() > 1) {
            log.info("TaskServiceImpl.getNextRoute formId ={},routeList={}" + formId, JSON.toJSONString(routeList));
            return null;
        }
        Route route = routeList.get(0);
        routeNextReq.setNextNodeId(route.getNextNodeId());
        routeNextReq.setRouteId(route.getRouteId());
        routeNextReq.setTaskId(task.getTaskId());
        return routeNextReq;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO nextRouteAndReceiveTask(NextRouteAndReceiveTaskReq req) {
        log.info("TaskServiceImpl.nextRouteAndReceiveTask req={}", JSON.toJSONString(req));
        if (req == null) {
            ResultVO.error(com.iwhalecloud.retail.dto.ResultCodeEnum.LACK_OF_PARAM);
        }
        // 根据formId获取流程下一步路由
        RouteNextReq routeNextReq = getNextRoute(req.getFormId());
        if (routeNextReq == null) {
            return ResultVO.error(ResultCodeEnum.NEXT_ROUTE_IS_EMPTY);
        }
        String taskItemId = routeNextReq.getTaskItemId();
        // 领取任务
        FlowTaskClaimReq taskClaimReq = new FlowTaskClaimReq();
        taskClaimReq.setTaskItemId(taskItemId);
        taskClaimReq.setUserId(req.getHandlerUserId());
        taskClaimReq.setUserName(req.getHandlerUserName());
        taskClaimReq.setTaskId(routeNextReq.getTaskId());
        ResultVO resultVO = receiveTask(taskClaimReq);
        if (!resultVO.isSuccess()) {
            return resultVO;
        }

        //修改业务参数值
        if (req.getParamsType() != null) {
            taskManager.updateTaskParams(routeNextReq.getTaskId(), req.getParamsType(), req.getParamsValue());
        }

        // 执行流程下一步
        routeNextReq.setHandlerUserId(req.getHandlerUserId());
        routeNextReq.setHandlerUserName(req.getHandlerUserName());
        routeNextReq.setHandlerMsg(req.getHandlerMsg());
        return nextRoute(routeNextReq);
    }

    @Override
    public ResultVO queryNextNodeRights(String nextNodeId,String taskId){
        log.info("TaskServiceImpl.queryNextNodeRights nextNodeId={}",nextNodeId);
        return taskManager.queryNextNodeRights(nextNodeId,taskId);
    }

}