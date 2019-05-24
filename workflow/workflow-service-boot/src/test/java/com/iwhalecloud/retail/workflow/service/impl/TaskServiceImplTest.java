package com.iwhalecloud.retail.workflow.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.WorkFlowServiceApplication;
import com.iwhalecloud.retail.workflow.bizservice.RunRouteService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import com.iwhalecloud.retail.workflow.dto.TaskItemDTO;
import com.iwhalecloud.retail.workflow.dto.req.*;
import com.iwhalecloud.retail.workflow.dto.resp.HandleTaskPageResp;
import com.iwhalecloud.retail.workflow.entity.*;
import com.iwhalecloud.retail.workflow.manager.*;
import com.iwhalecloud.retail.workflow.service.TaskItemService;
import com.iwhalecloud.retail.workflow.service.TaskService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = WorkFlowServiceApplication.class)
@RunWith(SpringRunner.class)
public class TaskServiceImplTest {

    @Resource
    private TaskService taskService;

    @Reference
    private TaskItemService taskItemService;

    @Resource
    private TaskItemManager taskItemManager;

    @Resource
    private RunRouteService runRouteService;

    @Resource
    private NodeRightsManager nodeRightsManager;

    @Resource
    private ProcessParamManager processParamManager;

    @Resource
    private AttrSpecManager attrSpecManager;

    @Resource
    private RuleDefManager ruleDefManager;

    @Test
    public void startProcess() {
        ProcessStartReq req = new ProcessStartReq();
        req.setProcessId("20190521");
        req.setTaskSubType("1130");
        req.setFormId("2019".concat(System.currentTimeMillis()+""));
        req.setTitle("流程匹配测试");
//        req.setApplyUserId("1");
//        req.setApplyUserName("admin");
        req.setParamsType(WorkFlowConst.TASK_PARAMS_TYPE.JSON_PARAMS.getCode());
        Map map=new HashMap();
        map.put("PP","huawei");
        req.setParamsValue(JSON.toJSONString(map));
        ResultVO resultVO = taskService.startProcess(req);
        System.out.println(resultVO.isSuccess());
        Assert.assertEquals("0", resultVO.getResultCode());
    }

    @Test
    public void receiveTask() {
        FlowTaskClaimReq req = new FlowTaskClaimReq();
        req.setTaskItemId("100016588");
        req.setTaskId("100016587");
        req.setUserId("1");
        req.setUserName("管理员");
        ResultVO resultVO = taskService.receiveTask(req);
        System.out.println(resultVO.isSuccess());

    }

    @Test
    public void nextRoute() {
        RouteNextReq req = new RouteNextReq();
        req.setTaskId("12213824");
        req.setTaskItemId("12213825");
        req.setHandlerUserId("1345");
        req.setHandlerUserName("12322");
        req.setHandlerMsg("选择");
        req.setRouteId("19");
        req.setNextNodeId("1007");
//        List<HandlerUser> userList = Lists.newArrayList();
//        HandlerUser user = new HandlerUser();
//        user.setHandlerUserId("1079205258011422722");
//        user.setHandlerUserName("经营主体");
//        req.setNextHandlerUser(userList);
        ResultVO resultVO = taskService.nextRoute(req);
        System.out.println(resultVO.isSuccess());
    }

    @Test
    public void addWorkTask() {
        WorkTaskAddReq req = new WorkTaskAddReq();
        req.setFormId("888889");
        req.setTaskTitle("创建工单测试");
        req.setCreateUserId("1");
        req.setCreateUserName("超级管理员");
        List<WorkTaskAddReq.UserInfo> userInfoList = Lists.newArrayList();
        WorkTaskAddReq.UserInfo userInfo = new WorkTaskAddReq.UserInfo();
        userInfo.setUserId("1");
        userInfo.setUserName("超级管理员");
        userInfoList.add(userInfo);
        req.setHandlerUsers(userInfoList);
        taskService.addWorkTask(req);
    }

    @Test
    public void handleWorkTask() {
        WorkTaskHandleReq req = new WorkTaskHandleReq();
        req.setFormId("888889");
        req.setHandlerUserId("1");
        req.setHandlerUserName("超级管理员");
        taskService.handleWorkTask(req);
    }

    @Test
    public void nextRouteAndReceiveTask() {
        NextRouteAndReceiveTaskReq req = new NextRouteAndReceiveTaskReq();
        req.setFormId("1106188514131267585");
        req.setHandlerUserId("1079205153170591745");
        req.setHandlerUserName("汤烛@东港农村信息化服务站");
        req.setHandlerMsg("测试");
        ResultVO<RouteNextReq> reqResultVO = taskService.nextRouteAndReceiveTask(req);
        System.out.println(reqResultVO.isSuccess());
        Assert.assertEquals("0", reqResultVO.getResultCode());
    }

    @Test
    public void queryTaskCnt(){
        TaskPageReq taskPageReq = new TaskPageReq();
        taskPageReq.setHandlerUserId("1");  //待处理的条件
        taskPageReq.setCreateUserId("1");   //我的申请条件

        ResultVO<Long> rv =  taskService.queryTaskCnt(taskPageReq);
        System.out.println(JSON.toJSONString(rv));
    }

    @Test
    public void queryHandleTaskPage(){
        HandleTaskPageReq handleTaskPageReq = new HandleTaskPageReq();
        handleTaskPageReq.setHandlerUserId("1");

        ResultVO<Page<HandleTaskPageResp>> rv =  taskService.queryHandleTaskPage(handleTaskPageReq);
        System.out.println(JSON.toJSONString(rv));
    }

    /**
     * 根据业务单号查询流程处理记录
     */
    @Test
    public void queryTaskItem() {
        TaskItemListReq req = new TaskItemListReq();
        req.setFormId("feca88cf27394cf2a394843b58803a36");
        ResultVO<List<TaskItemDTO>> rv = taskItemService.queryTaskItem(req);
        System.out.println(JSON.toJSONString(rv));
    }

    @Test
    public void getWaitDealTaskItemByFormId() {

        TaskItem taskItem = taskItemManager.queryWaitHandlerTaskItem("100016587");
        System.out.println(JSON.toJSONString(taskItem));
    }

    @Test
    public void getTaskDetail() {
        TaskDealDetailReq req = new TaskDealDetailReq();
        req.setTaskItemId("100016604");
        req.setTaskId("100016603");
        req.setUserId("1");
        req.setUserName("admin");

        System.out.println(JSON.toJSONString(taskService.dealTaskDetail(req)));
    }

    @Test
    public void queryNextNodeRights() {
        final String taskId = "100016603";
        final String nextNodeId = "1008";

        System.out.println(JSON.toJSONString(taskService.queryNextNodeRights(nextNodeId,taskId)));
    }

    @Test
    public void invokeRouteService() {

        InvokeRouteServiceRequest invokeRouteServiceRequest = new InvokeRouteServiceRequest();
        List<Service> serviceList = new ArrayList<>();
        Service service = new Service();
        service.setClassPath("com.iwhalecloud.retail.workflow.config.WfRunnable");
        service.setServiceGroup("goods");
        serviceList.add(service);

        service = new Service();
        service.setClassPath("com.iwhalecloud.retail.workflow.config.WfRunnable");
        service.setServiceGroup("partner");
        serviceList.add(service);
        runRouteService.invokeRouteService(invokeRouteServiceRequest,serviceList);
    }

    @Test
    public void getHandlerUserByRemote() {

    }
    @Test
    public void queryProcessParamByProcessId(){
        List<ProcessParam> processParam = processParamManager.queryProcessParamByProcessId("20190521");
        System.out.println(JSON.toJSONString(processParam));
    }
    @Test
    public void selectAttrSpecByAttrIds(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        List<AttrSpec> attrSpec = attrSpecManager.selectAttrSpecByAttrIds(list);
        System.out.println(JSON.toJSONString(attrSpec));
    }
    @Test
    public void queryRuleDefByParams(){
        List<RuleDef> ruleDef = ruleDefManager.queryRuleDefByParams("1","huawei");
        System.out.println(JSON.toJSONString(ruleDef));
    }

}