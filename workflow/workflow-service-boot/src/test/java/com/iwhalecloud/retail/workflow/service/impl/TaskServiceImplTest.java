package com.iwhalecloud.retail.workflow.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDetailDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantGetReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.UserGetReq;
import com.iwhalecloud.retail.system.service.UserService;
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

    @Reference
    private UserService userService;


    @Test
    public void startProcess() {
        ProcessStartReq req = new ProcessStartReq();
        req.setProcessId("528");
        req.setTaskSubType("3040");
        req.setFormId("1911");
        req.setTitle("采购申请单审核流程");

        req.setApplyUserId("22796");
//        req.setApplyUserName("admin");
        req.setParamsType(2);
        req.setParamsValue("10000696");
        req.setParamsType(WorkFlowConst.TASK_PARAMS_TYPE.JSON_PARAMS.getCode());
        Map map=new HashMap();
        map.put("CGJ","0");//
        req.setParamsValue(JSON.toJSONString(map));
        ResultVO resultVO = taskService.startProcess(req);
        System.out.println(resultVO.isSuccess());
//        Assert.assertEquals("0", resultVO.getResultCode());
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
//        String json = "{\"appendixType\":\"2\",\"appendixUrl\":\"\",\"handlerMsg\":\"333\",\"handlerUserId\":\"200012813991\",\"handlerUserName\":\"zte管理员\",\"nextHandlerUser\":[],\"nextNodeId\":\"1559\",\"routeId\":\"20190528005\",\"taskId\":\"12330136\",\"taskItemId\":\"12330137\"}\n";

          String json ="{\"routeId\":\"20190528004\",\"handlerMsg\":\"饿\",\"nextNodeId\":\"1549\",\"taskId\":\"12335986\",\"taskItemId\":\"12335996\",\"handlerUserId\":\"22796\",\"nextHandlerUser\":[],\"appendixType\":\"2\",\"appendixUrl\":\"\"}";
          json = "{\"routeId\":\"20190528009\",\"handlerMsg\":\"dsfdf \",\"nextNodeId\":\"1539\",\"taskId\":\"12337800\",\"taskItemId\":\"12338085\",\"handlerUserId\":\"10000692\",\"nextHandlerUser\":[]}";
          Gson gson = new Gson();
          RouteNextReq routeNextReq  = gson.fromJson(json, new TypeToken<RouteNextReq>(){}.getType());
//        req.setTaskId("12330129");
//        req.setTaskItemId("12330130");
//        req.setHandlerUserId("200012813991");
//        req.setHandlerUserName("zte管理员");
//        req.setHandlerMsg("555");
//        req.setRouteId("20190528005");
//        req.setNextNodeId("1559");
//        req.setAppendixType("2");
//        List<HandlerUser> userList = Lists.newArrayList();
//        HandlerUser user = new HandlerUser();
//        user.setHandlerUserId("1079205258011422722");
//        user.setHandlerUserName("经营主体");
//        req.setNextHandlerUser(userList);
          ResultVO resultVO = taskService.nextRoute(routeNextReq);
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

    @Test
    public  void  m() {
//        MerchantGetReq merchantGetReq = new MerchantGetReq();
//        merchantGetReq.setMerchantId("10000696");
//        ResultVO<MerchantDetailDTO> merchantInfo =merchantService.getMerchantDetail(merchantGetReq);
//        System.out.println("PurApplyGetHandlerUserIdServiceImpl.run merchantInfo={}"+ JSON.toJSONString(merchantInfo));

        UserGetReq userGetReq = new UserGetReq();
        userGetReq.setRelCode("10000696");
        UserDTO userDTO = userService.getUser(userGetReq);
        System.out.println("PurApplyGetHandlerUserIdServiceImpl.run merchantInfo={}"+ JSON.toJSONString(userDTO));

    }

    @Test
    public void startDiaoBoProcess() {
        ProcessStartReq req = new ProcessStartReq();
        req.setProcessId("7");
        req.setTaskSubType("1010");
        req.setFormId("12336295");
        req.setTitle("调拨审批流程");

        req.setApplyUserId("10000692");
        req.setApplyUserName("湖南龙裕实业有限公司_三星平台");
        req.setExtends1("长沙市分公司");
        req.setParamsType(WorkFlowConst.TASK_PARAMS_TYPE.JSON_PARAMS.getCode());
        Map map=new HashMap();
        map.put("731","731");//
        req.setParamsValue(JSON.toJSONString(map));
        ResultVO resultVO = taskService.startProcess(req);
        System.out.println(resultVO.isSuccess());
//        Assert.assertEquals("0", resultVO.getResultCode());
    }
}