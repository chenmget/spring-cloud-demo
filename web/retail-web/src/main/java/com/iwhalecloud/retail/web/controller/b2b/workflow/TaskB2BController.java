package com.iwhalecloud.retail.web.controller.b2b.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import com.iwhalecloud.retail.workflow.dto.TaskDTO;
import com.iwhalecloud.retail.workflow.dto.TaskItemDTO;
import com.iwhalecloud.retail.workflow.dto.req.*;
import com.iwhalecloud.retail.workflow.dto.resp.*;
import com.iwhalecloud.retail.workflow.service.TaskItemService;
import com.iwhalecloud.retail.workflow.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 任务查询控制类
 * @author Z
 * @date 2019/1/7
 */
@RestController
@RequestMapping("/api/b2b/task")
@Slf4j
@Api(value="待办/经办查询",tags={"待办/经办查询"})
public class TaskB2BController extends BaseController {


    @Reference
    private TaskService taskService;

    @Reference
    private TaskItemService taskItemService;

    @ApiOperation(value = "我的待办查询", notes = "我的待办查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="queryTaskPage")
    @UserLoginToken
    public ResultVO<Page<TaskPageResp>> queryTaskPage(@RequestBody TaskPageReq req) {
        //强制设置为当前登录用户
        req.setHandlerUserId(UserContext.getUserId());
        return taskService.queryTaskPage(req);
    }


    @ApiOperation(value = "我的经办查询", notes = "我的经办查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="queryHandleTaskPage")
    @UserLoginToken
    public ResultVO<Page<HandleTaskPageResp>> queryHandleTaskPage(@RequestBody HandleTaskPageReq req) {
        //强制设置为当前登录用户
        req.setHandlerUserId(UserContext.getUserId());
        return taskService.queryHandleTaskPage(req);
    }


    @ApiOperation(value = "获取待办详情", notes = "获取待办详情，如果待办未认领，则会先领取待办再加载待办信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="toDealTaskDetail")
    @UserLoginToken
    public ResultVO<DealTaskDetailGetResp> toDealTaskDetail(@RequestParam(value="taskItemId") String taskItemId,@RequestParam(value="taskId") String taskId) {

        UserDTO userDTO = UserContext.getUser();
        if (userDTO==null) {
            log.error("get login user error,taskItemId={}",taskItemId);
            return ResultVO.error("获取登录用户信息异常");
        }
        TaskDealDetailReq req = new TaskDealDetailReq();
        req.setTaskItemId(taskItemId);
        req.setTaskId(taskId);
        req.setUserId(userDTO.getUserId());
        req.setUserName(userDTO.getUserName());

        return taskService.dealTaskDetail(req);
    }

    @ApiOperation(value = "获取经办详情", notes = "获取经办详情")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="getHandleTaskDetail")
    @UserLoginToken
    public ResultVO<HandleTaskDetailGetResp> getHandleTaskDetail(@RequestParam(value="taskId") String taskId) {

        HandleTaskDetailReq req = new HandleTaskDetailReq();
        req.setTaskId(taskId);

        return taskService.getHandleTaskDetail(req);
    }

    @ApiOperation(value = "启动工作流", notes = "启动工作流")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/startProcess")
    public ResultVO startProcess(@RequestBody ProcessStartReq req){
        log.info("TaskB2BController startProcess req={} ", JSON.toJSON(req));
        return taskService.startProcess(req);
    }

    @ApiOperation(value = "领取任务", notes = "领取任务")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/receiveTask")
    public ResultVO receiveTask(@RequestBody FlowTaskClaimReq req){
        log.info("TaskB2BController receiveTask req={} ", JSON.toJSON(req));
        return taskService.receiveTask(req);
    }

    @ApiOperation(value = "流程下一步", notes = "流程下一步")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @UserLoginToken
    @PostMapping(value="/nextRoute")
    public ResultVO nextRoute(@RequestBody RouteNextReq req){
        log.info("TaskB2BController nextRoute req={} ", JSON.toJSON(req));
        String userId = UserContext.getUserId();
        req.setHandlerUserId(userId);
        if (UserContext.getUser() != null) {
            String userName = UserContext.getUser().getUserName();
            req.setHandlerUserName(userName);
        }
        return taskService.nextRoute(req);
    }

    @ApiOperation(value = "执行下一步并领取任务", notes = "执行下一步并领取任务")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @UserLoginToken
    @PostMapping(value="/nextRouteAndReceiveTask")
    public ResultVO nextRouteAndReceiveTask(@RequestBody NextRouteAndReceiveTaskReq req){
        log.info("TaskB2BController nextRouteAndReceiveTask req={} ", JSON.toJSON(req));
        return taskService.nextRouteAndReceiveTask(req);
    }

    @ApiOperation(value = "获取流程处理环节信息", notes = "获取流程处理环节信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="queryTaskItem")
    @UserLoginToken
    public ResultVO<List<TaskItemDTO>> queryTaskItem(@RequestParam(value="formId") String formId) {

        TaskItemListReq taskItemListReq = new TaskItemListReq();
        taskItemListReq.setFormId(formId);

        return taskItemService.queryTaskItem(taskItemListReq);
    }

    @ApiOperation(value = "获取下一环节可处理人",notes = "获取环节可处理人")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="queryNextNodeRights")
    @UserLoginToken
    public ResultVO queryNextNodeRights(@RequestParam String nextNodeId,@RequestParam String taskId){

        return taskService.queryNextNodeRights(nextNodeId,taskId);
    }

    @ApiOperation(value = "根据业务ID获取处理中的工作流信息",notes = "获取工作流")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/getTaskByFormId")
    public ResultVO getTaskByFormId(@RequestParam String formId){
        log.info("TaskB2BController getTaskByFormId formId={} ", formId);
        QueryTaskByFormIdResp queryTaskByFormIdResp = new QueryTaskByFormIdResp();
        TaskDTO taskDTO = taskService.getTaskByFormId(formId).getResultData().get(0);
        TaskItemDTO taskItemDTO = taskItemService.queryTaskItemByTaskId(taskDTO.getTaskId()).getResultData();
        queryTaskByFormIdResp.setTaskDTO(taskDTO);
        queryTaskByFormIdResp.setTaskItemDTO(taskItemDTO);
        queryTaskByFormIdResp.setFormId(formId);
        return ResultVO.success(queryTaskByFormIdResp);
    }

}
