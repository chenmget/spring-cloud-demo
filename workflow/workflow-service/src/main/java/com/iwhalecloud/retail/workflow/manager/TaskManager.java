package com.iwhalecloud.retail.workflow.manager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.UserDetailDTO;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.workflow.bizservice.RunRouteService;
import com.iwhalecloud.retail.workflow.common.ResultCodeEnum;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import com.iwhalecloud.retail.workflow.dto.req.*;
import com.iwhalecloud.retail.workflow.dto.resp.HandleTaskPageResp;
import com.iwhalecloud.retail.workflow.dto.resp.TaskPageResp;
import com.iwhalecloud.retail.workflow.entity.*;
import com.iwhalecloud.retail.workflow.entity.Process;
import com.iwhalecloud.retail.workflow.extservice.WFServiceExecutor;
import com.iwhalecloud.retail.workflow.extservice.params.NodeRightsServiceParamContext;
import com.iwhalecloud.retail.workflow.mapper.TaskMapper;
import com.iwhalecloud.retail.workflow.model.TaskItemDetailModel;
import com.iwhalecloud.retail.workflow.sal.system.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 任务相关业务处理类
 *
 * @author z
 */
@Component
@Slf4j
public class TaskManager extends ServiceImpl<TaskMapper,Task> {

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private TaskItemManager taskItemManager;

    @Resource
    private ProcessManager processManager;

    @Resource
    private RouteManager routeManager;

    @Resource
    private NodeRightsManager nodeRightsManager;

    @Resource
    private RouteServiceManager routeServiceManager;

    @Resource
    private ServiceManager serviceManager;

    @Resource
    private TaskPoolManager taskPoolManager;

    @Reference
    private UserService userService;

    @Resource
    private UserClient userClient;

    @Resource
    private RunRouteService runRouteService;

    /**
     * 启动工作流
     * @param processStartDTO
     * @return
     */
    public ResultVO startProcess(ProcessStartReq processStartDTO) {


        String processId = processStartDTO.getProcessId();
        log.info("1、根据流程ID查询流程（wf_process表），process_id={}",processStartDTO.getProcessId());
        Process process = processManager.queryProcessById(processId);
        if (process == null) {
            return ResultVO.error(ResultCodeEnum.FLOW_IS_EMPTY);
        }
        log.info("2、根据流程ID和开始节点获取当前需要处理的路由信息（wf_route）wf_route，process_id={}，node_id={}"
                ,processStartDTO.getProcessId(), WorkFlowConst.WF_NODE.NODE_START.getId());
        //如果没有查询到路由信息获取多条路由信息，抛出异常
        List<Route> routeList = routeManager.listRoute(processId, WorkFlowConst.WF_NODE.NODE_START.getId());
        if (CollectionUtils.isEmpty(routeList) || routeList.size() > 1) {
            return ResultVO.error(ResultCodeEnum.ROUTE_NOT_EQUAL_ONE);
        }
        Route route = routeList.get(0);
        String routeId = route.getRouteId();
        String nextNodeId = route.getNextNodeId();

        log.info("3、根据第1点和入参信息，添加任务实例表数据（wf_task）");
        String formId = processStartDTO.getFormId();
        String title = processStartDTO.getTitle();
        String applyUserId = processStartDTO.getApplyUserId();
        String applyUserName = processStartDTO.getApplyUserName();
        String taskType = WorkFlowConst.TaskType.FLOW.getCode();
        String taskStatus = WorkFlowConst.TaskState.HANDING.getCode();
        String taskSubType = processStartDTO.getTaskSubType();
        Task task = addTask(formId,title,processId,applyUserId,applyUserName, taskType, taskStatus
                , taskSubType,nextNodeId,route.getNextNodeName(),processStartDTO.getExtends1());
        //@todo 补全route_id,serviceList日志
        //如果serviceList为空，忽略第6部
        log.info("4、根据路由信息查询路由服务（wf_route_service）表，获取需要执行的服务");
        List<RouteService> routeServiceList = routeServiceManager.listRouteService(routeId);
        log.info("TaskManager.startProcess route_id={},routeServiceList={}", routeId, JSON.toJSONString(routeServiceList));
        log.info("5、构造服务运行的容器对象，调用配置的服务");
        invokeRouteService(formId,applyUserId,applyUserName,routeServiceList);
        log.info("6、根据路由信息的下一环节ID查询环节权限表（wf_node_rights）,获取允许处理当前环节的用户列表");
        List<NodeRights> nodeRightsList = nodeRightsManager.listNodeRights(nextNodeId);
        if (CollectionUtils.isEmpty(nodeRightsList)) {
            log.error("TaskManager.startProcess NOTE_RIGHTS_IS_EMPTY formId = {}", formId);
            throw new RuntimeException(ResultCodeEnum.NOTE_RIGHTS_IS_EMPTY.getDesc());
        }
        // 添加任务项
        List<HandlerUser> handlerUserList = processStartDTO.getNextHandlerUser();
        addNextTaskItem(route, task, nodeRightsList, handlerUserList);
        return ResultVO.success();
    }

    /**
     * 添加任务项
     * @return
     */
    private void addNextTaskItem(Route route, Task task, List<NodeRights> nodeRightsList,List<HandlerUser> handlerUserList) {
        Boolean handlerUserListEmpty = handlerUserList == null || handlerUserList.isEmpty();
        TaskItem taskItem = new TaskItem();
        if (handlerUserListEmpty && nodeRightsList.size() == 1 && WorkFlowConst.RightsType.User.getId().
            equals(nodeRightsList.get(0).getRightsType())) {
            NodeRights nodeRights = nodeRightsList.get(0);
            String handlerUserId = nodeRights.getUserId();
            UserDTO userDTO = userService.getUserByUserId(handlerUserId);
            String handlerUserName = userDTO.getUserName();
            taskItem.setHandlerUserId(handlerUserId);
            taskItem.setHandlerUserName(handlerUserName);
        }

        final String taskId = task.getTaskId();
        taskItem.setRouteId(route.getRouteId());
        taskItem.setRouteName(route.getRouteName());
        taskItem.setPreNodeId(route.getCurNodeId());
        taskItem.setPreNodeName(route.getCurNodeName());
        taskItem.setCurNodeId(route.getNextNodeId());
        taskItem.setCurNodeName(route.getNextNodeName());
        taskItem.setTaskId(taskId);
        taskItem.setTaskType(WorkFlowConst.TaskType.FLOW.getCode());
        taskItem.setItemStatus(WorkFlowConst.TaskItemState.WAITING.getCode());
        String taskItemId = taskItemManager.addTaskItem(taskItem);
        if (handlerUserListEmpty) {
            for (NodeRights nodeRights : nodeRightsList) {
                List<HandlerUser> handlerUsers = nodeRightsManager.listUserByRightsType(nodeRights, task);
                log.info("TaskManager.addNextTaskItem taskItemId={},handlerUsers={}", taskItemId, JSON.toJSONString(handlerUsers));
                for (HandlerUser user : handlerUsers) {
                    log.info("7、添加流程实例任务池数据（wf_task_pool）");
                    taskPoolManager.addTaskPool(taskItemId, taskId, user.getHandlerUserId(), user.getHandlerUserName());
                }
            }
        } else {
            for (HandlerUser item : handlerUserList) {
                log.info("TaskManager.addNextTaskItem taskItemId={},handlerUsers={}", taskItemId, JSON.toJSONString(handlerUserList));
                log.info("7、添加流程实例任务池数据（wf_task_pool）");
                taskPoolManager.addTaskPool(taskItemId, taskId, item.getHandlerUserId(), item.getHandlerUserName());
            }
        }
    }

    /**
     * 调用路由服务
     * @return
     */
    private void invokeRouteService(String formId,String handlerUserId,String handlerUserName,List<RouteService> routeServiceList) {
        if (CollectionUtils.isNotEmpty(routeServiceList)) {
            List<String> serviceIdList = routeServiceList.stream().map(RouteService::getServiceId).collect(Collectors.toList());
            List<Service> serviceList = serviceManager.listServiceByIds(serviceIdList);
            if (CollectionUtils.isNotEmpty(serviceList)) {
                // 调用服务
                InvokeRouteServiceRequest invokeRouteServiceRequest = new InvokeRouteServiceRequest();
                invokeRouteServiceRequest.setBusinessId(formId);
                invokeRouteServiceRequest.setHandlerUserId(handlerUserId);
                invokeRouteServiceRequest.setHandlerUserName(handlerUserName);
                runRouteService.invokeRouteService(invokeRouteServiceRequest, serviceList);
            }
        }
    }

    /**
     * 添加任务
     * @return
     */
    private Task addTask(String formId, String title, String processId, String applyUserId,
                           String applyUserName, String taskType, String taskStatus, String taskSubType,
                           String curNodeId,String curNodeName,String extends1) {

        UserDetailDTO userDetailDTO = userClient.getUserDetail(applyUserId);

        Task task = new Task();
        task.setFormId(formId);
        task.setTaskTitle(title);
        task.setProcessId(processId);
        task.setTaskType(taskType);
        task.setTaskSubType(taskSubType);
        task.setTaskStatus(taskStatus);
        task.setCreateUserId(applyUserId);
        task.setCreateUserName(getUserDisplayName(userDetailDTO,applyUserName));
        task.setCreateTime(new Date());
        task.setCurNodeId(curNodeId);
        task.setCurNodeName(curNodeName);
        task.setLastDealTime(new Date());
        task.setExtends1(getExtends1(userDetailDTO,extends1));
        taskMapper.insert(task);
        return task;
    }

    private String getExtends1(UserDetailDTO user,String extends1) {
        if (user == null || StringUtils.isEmpty(extends1)) {
            return "";
        }

        //如果申请人是商家，该字段信息显示“地市+区县”信息，如果是电信人员则显示“岗位+部门”信息
        if (isMerchant(user.getUserFounder())) {
            return StringUtils.trimToEmpty(user.getLanName()) + "-" + StringUtils.trimToEmpty(user.getRegionName());
        }

        return StringUtils.trimToEmpty(user.getSysPostName()) + "-" + StringUtils.trimToEmpty(user.getOrgName());
    }

    /**
     * 获取需要展示的名称
     * @param user
     * @param userName
     * @return
     */
    private String getUserDisplayName(UserDetailDTO user,String userName) {
        if (user == null || StringUtils.isEmpty(userName)) {
            return "";
        }
        //申请人如果是商家，“申请人”就显示商家名称，如果是电信管理员则显示姓名
        if (isMerchant(user.getUserFounder())) {
            return user.getMerchantName();
        }

        return userName;
    }

    /**
     * 判断是否是商家
     * @param userFounder
     * @return
     */
    private boolean isMerchant(int userFounder) {
        if (SystemConst.UserFounderEnum.PARTNER.getCode() == userFounder
                || SystemConst.UserFounderEnum.SUPPLIER_PROVINCE.getCode() == userFounder
                || SystemConst.UserFounderEnum.SUPPLIER_GROUND.getCode() == userFounder
                || SystemConst.UserFounderEnum.MANUFACTURER.getCode() == userFounder) {
            return true;
        }

        return false;
    }

    /**
     * 流程下一步
     * @return
     */
    public ResultVO nextRoute(RouteNextReq routeNextDTO) {

        String taskItemId = routeNextDTO.getTaskItemId();
        String taskId = routeNextDTO.getTaskId();
        if (StringUtils.isEmpty(taskItemId)) {
            log.error("TaskManager.nextRoute-->taskItemId param is null");
            return ResultVO.errorEnum(ResultCodeEnum.TASK_ITEM_ID_IS_EMPTY);
        }
        if (StringUtils.isEmpty(taskId)) {
            log.error("TaskManager.nextRoute-->taskId param is null");
            return ResultVO.errorEnum(ResultCodeEnum.TASK_ID_IS_EMPTY);
        }

        log.info("1、根据任务项ID查询任务项（wf_task_item）表，task_item_id={},taskId={}", taskItemId,taskId);
        TaskItem taskItem = taskItemManager.queryTaskItemById(taskItemId,taskId);
        if (taskItem == null) {
            return ResultVO.errorEnum(ResultCodeEnum.TASK_ITEM_IS_EMPTY);
        }
        //1> 校验任务项校验处理人是否一致
        if (!taskItem.getHandlerUserId().equals(routeNextDTO.getHandlerUserId())) {
            return ResultVO.error(ResultCodeEnum.TASK_ITEM_HANDLER_USER_DIFFER);
        }
        //2> 校验任务项是否已经处理
        if (WorkFlowConst.TaskItemState.HANDLED.equals(taskItem.getItemStatus())) {
            return ResultVO.error(ResultCodeEnum.TASK_ITEM_IS_HANDLED);
        }
        String handleUserId = routeNextDTO.getHandlerUserId();
        String handleUserName = routeNextDTO.getHandlerUserName();
        String handleMsg = routeNextDTO.getHandlerMsg();
        //3> 修改任务项的状态、任务办理时间、任务处理人、任务处理人名称、处理意见
        taskItemManager.taskHandle(taskItemId, handleUserId, handleUserName, handleMsg,taskId);
        //如果下一个节点为结束节点，则忽略这个步骤
        log.info("2、根据路由信息的下一环节ID查询环节权限表（wf_node_rights）,获取允许处理当前环节的用户列表，next_node_id={}，userList={}");
        //如果没有查询到用户列表，抛出异常
        String noteNextId = routeNextDTO.getNextNodeId();
        log.info("3、根据任务项的路由ID信息查询路由服务（wf_route_service）表，获取需要执行的服务,route_id={},serviceList={}");
        String routeId = routeNextDTO.getRouteId();
        List<RouteService> routeServiceList = routeServiceManager.listRouteService(routeId);
        log.info("TaskManager.nextRoute route_id={},routeServiceList={}", routeId, JSON.toJSONString(routeServiceList));
        log.info("4、构造服务运行的容器对象，调用配置的服务");
        Task task =  taskMapper.selectById(taskId);
        String formId = task.getFormId();
        invokeRouteService(formId,handleUserId,handleUserName,routeServiceList);
        Route route = routeManager.queryRouteById(routeId);
        log.info("5、如果下一个节点为结束节点，修改任务实例表的状态、办结时间，忽略后面的步骤");
        if (WorkFlowConst.WF_NODE.NODE_END.getId().equals(noteNextId)) {
            nodeEndHandle(handleUserId, handleUserName, taskId, route);
            return ResultVO.success();
        } else {
            //将最新的环节信息冗余到任务实例表
            updateTaskStatusById(taskId, WorkFlowConst.TASK_STATUS_PROCESSING,route.getNextNodeId(),route.getNextNodeName());
        }
        List<NodeRights> nodeRightsList = nodeRightsManager.listNodeRights(noteNextId);
        if (CollectionUtils.isEmpty(nodeRightsList)) {
            log.error("TaskManager.nextRoute NOTE_RIGHTS_IS_EMPTY taskItemId = {}", routeNextDTO.getTaskItemId());
            throw new RuntimeException(ResultCodeEnum.NOTE_RIGHTS_IS_EMPTY.getDesc());
        }
        //如果第2点只查询出一个用户，直接填写任务分配时间和处理人信息，忽略第7步
        log.info("6、根据第1点和第2点和入参信息，添加路由实例表数据（wf_task_item）");
        addNextTaskItem(route,task,nodeRightsList,routeNextDTO.getNextHandlerUser());
        return ResultVO.success();
    }

    /**
     * 结束节点处理
     * @return
     */
    private void nodeEndHandle(String handleUserId, String handleUserName, String taskId, Route route) {
        updateTaskStatusById(taskId, WorkFlowConst.TASK_STATUS_FINISH,route.getNextNodeId(),route.getNextNodeName());

        TaskItem taskItem = new TaskItem();
        taskItem.setHandlerUserId(handleUserId);
        taskItem.setHandlerUserName(handleUserName);
        taskItem.setRouteId(route.getRouteId());
        taskItem.setRouteName(route.getRouteName());
        taskItem.setPreNodeId(route.getCurNodeId());
        taskItem.setPreNodeName(route.getCurNodeName());
        taskItem.setCurNodeId(route.getNextNodeId());
        taskItem.setCurNodeName(route.getNextNodeName());
        taskItem.setTaskId(taskId);
        taskItem.setTaskType(WorkFlowConst.TaskType.FLOW.getCode());
        taskItem.setItemStatus(WorkFlowConst.TaskItemState.HANDLED.getCode());
        taskItemManager.addTaskItem(taskItem);
    }


    /**
     * 领取任务（流程使用）
     * @return
     */
    public ResultVO receiveTask(FlowTaskClaimReq taskClaimDTO) {

        String taskItemId = taskClaimDTO.getTaskItemId();
        String taskId = taskClaimDTO.getTaskId();
        log.info("1、根据任务项ID查询任务项（wf_task_item）表，task_item_id={},taskId={}", taskItemId,taskId);
        //1> 校验任务项的状态,如果状态不是待领取抛出异常
        TaskItem taskItem = taskItemManager.queryTaskItemById(taskItemId,taskId);
        if (taskItem == null || !WorkFlowConst.TaskItemState.WAITING.getCode().equals(taskItem.getItemStatus())) {
            return ResultVO.error(ResultCodeEnum.TASK_ITEM_STATE_NOT_IS_WAITING);
        }
        log.info("2、根据任务项ID查询任务池表（wf_task_pool）表，task_item_id={}", taskItemId);
        //1> 校验用户ID是否在任务池的列表中，如果不存在抛出异常
        List<TaskPool> taskPoolList = taskPoolManager.queryTaskPoolByTaskItemId(taskItemId,taskId);
        if (CollectionUtils.isEmpty(taskPoolList)) {
            return ResultVO.error(ResultCodeEnum.TASK_POOL_IS_EMPTY);
        }
        List<TaskPool> taskPools = taskPoolList.stream().filter(taskPool -> taskPool.getUserId().equals(taskClaimDTO.getUserId())).collect(Collectors.toList());
        if (taskPools.size() < 1) {
            return ResultVO.error(ResultCodeEnum.USER_NOT_IN_TASK_POOL);
        }
        log.info("3、修改任务项（wf_task_item）表的状态、处理人信息、任务领取时间，task_item_id={}", taskItemId);
        taskItemManager.taskReceive(taskItemId, taskClaimDTO.getUserId(), taskClaimDTO.getUserName(), taskId);
        log.info("4、根据任务项ID删除任务池表（wf_task_pool）数据");
        //@todo如果是工单类的，路由ID、上一节点ID、当前节点ID为-1，上一节点名称和当前节点名称为空
        taskPoolManager.delTaskPoolByTaskItemId(taskItemId,taskId);
        return ResultVO.success();
    }


    /**
     * 创建工单
     * @param taskAddReq
     * @return
     */
    public ResultVO addWorkTask(WorkTaskAddReq taskAddReq) {

        log.info("1、添加任务实例表数据（wf_task）表");
        String formId = taskAddReq.getFormId();
        String taskTitle = taskAddReq.getTaskTitle();
        String userId = taskAddReq.getCreateUserId();
        String userName = taskAddReq.getCreateUserName();
        String taskType = WorkFlowConst.TaskType.WORK.getCode();
        if (!StringUtils.isEmpty(taskAddReq.getTaskType())) {
            taskType = taskAddReq.getTaskType();
        }
        String taskStatus = WorkFlowConst.TaskState.HANDING.getCode();
        String taskSubType = WorkFlowConst.WORK_TYPE;
        if (!StringUtils.isEmpty(taskAddReq.getTaskSubType())) {
            taskSubType = taskAddReq.getTaskSubType();
        }
        Task task = addTask(formId, taskTitle, WorkFlowConst.WORK_TYPE, userId, userName,
                    taskType,taskStatus, taskSubType,WorkFlowConst.WORK_TYPE,
                    taskAddReq.getNextNodeName(),taskAddReq.getExtends1());
        final String taskId = task.getTaskId();
        log.info("2、根据传入的信息添加任务项（wf_task_item）表");
        //如果传入的处理用户列表只有1个，直接填写任务分配时间和处理人信息，忽略第3步
        TaskItem taskItem = new TaskItem();
        taskItem.setTaskId(taskId);
        taskItem.setTaskType(taskType);
        taskItem.setRouteId(WorkFlowConst.WORK_TYPE);
        taskItem.setRouteName(WorkFlowConst.WORK_TYPE);
        taskItem.setPreNodeId(WorkFlowConst.WORK_TYPE);
        taskItem.setPreNodeName(taskAddReq.getPreNodeName());
        taskItem.setCurNodeId(WorkFlowConst.WORK_TYPE);
        taskItem.setCurNodeName(taskAddReq.getNextNodeName());
        taskItem.setItemStatus(WorkFlowConst.TaskItemState.PENDING.getCode());
        List<WorkTaskAddReq.UserInfo> userInfoList = taskAddReq.getHandlerUsers();
        if (CollectionUtils.isNotEmpty(userInfoList) || userInfoList.size() == 1) {
            WorkTaskAddReq.UserInfo userInfo = userInfoList.get(0);
            taskItem.setHandlerUserId(userInfo.getUserId());
            taskItem.setHandlerUserName(userInfo.getUserName());
            taskItem.setAssignTime(new Date());
            taskItemManager.addTaskItem(taskItem);
            return ResultVO.success();
        }
        String taskItemId = taskItemManager.addTaskItem(taskItem);
        log.info("3、根据传入的处理用户，添加流程实例任务池数据（wf_task_pool）");
        if (CollectionUtils.isNotEmpty(userInfoList)) {
            for (WorkTaskAddReq.UserInfo userInfo : userInfoList) {
                taskPoolManager.addTaskPool(taskItemId, taskId, userInfo.getUserId(), userInfo.getUserName());
            }
        }
        return ResultVO.success();
    }


    /**
     * 处理工单
     * @param workTaskHandleReq
     * @return
     */
    public ResultVO handleWorkTask(WorkTaskHandleReq workTaskHandleReq) {

        log.info("1、查询任务项（wf_task_item）表，workTaskHandleReq={}", JSON.toJSONString(workTaskHandleReq));
        //1> 校验任务项是否已经处理
        //2> 修改任务项的状态、任务办理时间、任务处理人、任务处理人名称
        String formId = workTaskHandleReq.getFormId();
        if (StringUtils.isEmpty(formId)) {
            return ResultVO.error("formId不能为空");
        }
        List<Task> tasks = this.getTaskByCondition(workTaskHandleReq);
        if (CollectionUtils.isEmpty(tasks) || tasks.size() > 1) {
            log.info("TaskManager.handleWorkTask query taskList error formId ={}" + formId);
            return ResultVO.error(ResultCodeEnum.TASK_LIST_IS_ERROR);
        }
        final Task task = tasks.get(0);
        TaskItem taskItem = taskItemManager.queryWaitHandlerTaskItem(task.getTaskId());
        if (taskItem == null) {
            return ResultVO.error(ResultCodeEnum.TASK_ITEM_IS_EMPTY);
        } else if (WorkFlowConst.TaskItemState.HANDLED.getCode().equals(taskItem.getItemStatus())) {
            return ResultVO.error(ResultCodeEnum.TASK_ITEM_IS_HANDLED);
        }
        log.info("2、修改任务实例表的状态、办结时间");

        taskItemManager.taskHandle(taskItem.getTaskItemId(), workTaskHandleReq.getHandlerUserId(), workTaskHandleReq.getHandlerUserName(), null,task.getTaskId());
        updateTaskStatusById(taskItem.getTaskId(), WorkFlowConst.TASK_STATUS_FINISH, null, null);
        log.info("3、根据任务项ID删除任务池表（wf_task_pool）数据");
        taskPoolManager.delTaskPoolByTaskItemId(taskItem.getTaskItemId(),task.getTaskId());
        return ResultVO.success();
    }

    /**
     * 待办查询
     * @param req 待办查询对象
     * @return
     */
    public Page<TaskPageResp> queryTaskPage(TaskPageReq req) {

        Page<TaskPageResp> page = new Page<TaskPageResp>(req.getPageNo(),req.getPageSize());

        return taskMapper.queryTask(page,req);
    }

    /**
     * 查询我的待办总数
     * @param req 待办查询对象
     * @return
     */
    public Long queryTaskCnt(TaskPageReq req) {
        return taskMapper.queryTaskCnt(req);
    }

    /**
     * 查询我的经办
     * @param req 经办查询对象
     * @return
     */
    public Page<HandleTaskPageResp> queryHandleTask(HandleTaskPageReq req) {
        Page<TaskPageResp> page = new Page<TaskPageResp>(req.getPageNo(),req.getPageSize());

        return taskMapper.queryHandleTask(page, req);
    }

    /**
     * 查询我的经办总数
     * @param req 经办查询对象
     * @return
     */
    public Long queryHandleTaskCnt(HandleTaskPageReq req) {
        return taskMapper.queryHandleTaskCnt(req);
    }

    /**
     * 获取待办项详情
     * @param taskItemId
     * @return
     */
    public TaskItemDetailModel getTaskItemDetail(String taskId,String taskItemId) {

        TaskItem taskItem = taskItemManager.queryWaitHandlerTaskItem(taskId, taskItemId);
        if (taskItem == null) {
            log.warn("TaskManager.getTaskItemDetail taskItem is null,taskId={},taskItemId={}",taskId,taskItemId);
            return null;
        }

        Task task = this.getTask(taskId);
        if (task == null) {
            log.warn("TaskManager.getTaskItemDetail task is null,taskId={}",taskId);
            return null;
        }
        TaskItemDetailModel taskItemDetailModel = new TaskItemDetailModel();
        BeanUtils.copyProperties(task,taskItemDetailModel);
        BeanUtils.copyProperties(taskItem,taskItemDetailModel);

        return taskItemDetailModel;
    }

    /**
     * 获取任务
     * @param taskId 任务ID
     * @return
     */
    public Task getTask(String taskId) {
        return taskMapper.selectById(taskId);
    }


    /**
     * 更新任务状态
     * @param taskId
     * @return
     */
    private Boolean updateTaskStatusById(String taskId, String taskStatus,String curNodeId,String curNodeName) {
        Task task = new Task();
        //task.setTaskId(taskId);
        task.setTaskStatus(taskStatus);
        task.setCurNodeId(curNodeId);
        task.setCurNodeName(curNodeName);
        task.setLastDealTime(new Date());

        UpdateWrapper<Task> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(Task.FieldNames.taskId.getTableFieldName(),taskId);
        return taskMapper.update(task,updateWrapper) > 0;
    }

    /**
     * 获取待处理的任务
     * @param formId 业务ID
     * @return
     */
    public List<Task> getTaskByFormId(String formId) {
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        //只查询待处理的
        queryWrapper.eq(Task.FieldNames.taskStatus.getTableFieldName(), WorkFlowConst.TaskState.HANDING.getCode());
        queryWrapper.eq("form_id", formId);

        return taskMapper.selectList(queryWrapper);
    }

    /**
     * 根据条件获取待处理的任务
     *
     * @param req
     * @return
     */
    public List<Task> getTaskByCondition(WorkTaskHandleReq req) {
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        //只查询待处理的
        queryWrapper.eq(Task.FieldNames.taskStatus.getTableFieldName(), WorkFlowConst.TaskState.HANDING.getCode());
        queryWrapper.eq(Task.FieldNames.formId.getTableFieldName(), req.getFormId());
        if (!StringUtils.isEmpty(req.getTaskType())) {
            queryWrapper.eq(Task.FieldNames.taskType.getTableFieldName(), req.getTaskType());
        }
        if (!StringUtils.isEmpty(req.getTaskSubType())) {
            queryWrapper.eq(Task.FieldNames.taskSubType.getTableFieldName(), req.getTaskSubType());
        }
        return taskMapper.selectList(queryWrapper);
    }

    /**
     * 获取下一环节可处理人
     * @param nextNodeId
     * @return
     */
    public ResultVO queryNextNodeRights(String nextNodeId,String taskId) {
        Task task =  taskMapper.selectById(taskId);
        List<NodeRights> nodeRightsList = nodeRightsManager.listNodeRights(nextNodeId);
        List<HandlerUser> userList = new ArrayList<>();
        for (NodeRights nodeRights : nodeRightsList) {
            List<HandlerUser> handlerUsers = nodeRightsManager.listUserByRightsType(nodeRights, task);
            for (HandlerUser user : handlerUsers) {
                userList.add(user);
            }
        }

        return ResultVO.success(userList);
    }


    private WFServiceExecutor serviceExecutor;

    /**
     * 调用远程服务获取节点的权限
     * @return
     */
    private ResultVO<List<HandlerUser>> invokeNodeRightsService(String formId,String roleId,String handlerUserId,String handlerUserName,String serviceId) {

//        Service service = serviceManager.getService(serviceId);
        Service service = new Service();

        NodeRightsServiceParamContext paramContext = new NodeRightsServiceParamContext();
        paramContext.setBusinessId(formId);
        paramContext.setBusinessId(handlerUserId);
        paramContext.setHandlerUserName(handlerUserName);
        paramContext.setRoleId(roleId);
        paramContext.setDynamicParam(service.getDynamicParam());


        return serviceExecutor.execute(paramContext);
    }
}
