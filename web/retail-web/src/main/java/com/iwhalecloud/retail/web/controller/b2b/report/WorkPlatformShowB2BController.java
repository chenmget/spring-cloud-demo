package com.iwhalecloud.retail.web.controller.b2b.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ProductDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductCountGetReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.order2b.dto.response.OrderWorkPlatformShowResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderStatisticsReq;
import com.iwhalecloud.retail.order2b.service.OrderStatisticsService;
import com.iwhalecloud.retail.partner.dto.MerchantLimitDTO;
import com.iwhalecloud.retail.partner.service.MerchantLimitService;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.system.service.NoticeUserService;
import com.iwhalecloud.retail.system.service.SysUserMessageService;
import com.iwhalecloud.retail.warehouse.service.ResourceInstStoreService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.dto.WorkPlatformMsgDTO;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.HandleTaskPageReq;
import com.iwhalecloud.retail.workflow.dto.req.TaskPageReq;
import com.iwhalecloud.retail.workflow.dto.resp.TaskPageResp;
import com.iwhalecloud.retail.workflow.service.TaskService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/b2b/workPlatformShow")
@Slf4j
public class WorkPlatformShowB2BController {
//    @Reference
//    private WorkPlatformShowService workPlatformShowService;

    @Reference
    private NoticeUserService noticeUserService;

    @Reference
    private ResourceInstStoreService resourceInstStoreService;

    @Reference
    private OrderStatisticsService orderStatisticsService;

    @Reference
    private ProductService productService;

    @Reference
    private TaskService taskService;

    @Reference
    private MerchantRulesService merchantRulesService;

    @Reference
    private MerchantLimitService merchantLimitService;
    
    @Reference
    private SysUserMessageService sysUserMessageService;

    @ApiOperation(value = "工作台待处理、我的申请、我的消息", notes = "工作台缩略信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/getWorkPlatformMsg")
    @UserLoginToken
    public ResultVO<WorkPlatformMsgDTO> getWorkPlatformMsg(){
        WorkPlatformMsgDTO dto = new WorkPlatformMsgDTO();
        String userId = UserContext.getUserId();
        dto.setUnhandledItemCount(getUnhandledItemCount(userId));
        dto.setAppliedItemCount(getAppliedItemCount(userId));
        dto.setNotReadNoticeCount(noticeUserService.getNotReadNoticeCount(userId).getResultData());
        dto.setSysAlarmCount(getSysUserMessage(userId, "2"));
        
        return ResultVO.success(dto);
    }

    @ApiOperation(value = "查询我的待办", notes = "查询我的待办。出参模板：" +
            " \"任务类型ID\": [\n" +
            "            {\n" +
            "                \"taskId\": \"任务ID\",\n" +
            "                \"formId\": \"主单ID\",\n" +
            "                \"taskTitle\": \"任务标题\",\n" +
            "                \"taskType\": \"任务大类\",\n" +
            "                \"taskSubType\": \"任务类型\",\n" +
            "                \"createUserName\": \"申请人\",\n" +
            "                \"createTime\": 创建时间,\n" +
            "                \"taskItemId\": \"任务项ID\",\n" +
            "                \"curNodeName\": \"当前节点\",\n" +
            "                \"itemStatus\": \"状态（1：待领取   2：待处理   3：已处理）\",\n" +
            "                \"assignTime\": 任务分派时间\n" +
            "            }\n" +
            "        ]。比如：\"resultData\": {\n" +
            "        \"1\": [\n" +
            "            {\n" +
            "                \"taskId\": \"TASK_1001\",\n" +
            "                \"formId\": \"FORM_1001\",\n" +
            "                \"taskTitle\": \"厂家串码管理流程\",\n" +
            "                \"taskType\": \"1\",\n" +
            "                \"taskSubType\": \"1\",\n" +
            "                \"createUserName\": \"管理员\",\n" +
            "                \"createTime\": 1546910669000,\n" +
            "                \"taskItemId\": \"ITEM_1001\",\n" +
            "                \"curNodeName\": \"产品初审\",\n" +
            "                \"itemStatus\": \"2\",\n" +
            "                \"assignTime\": 1546927452000\n" +
            "            },\n" +
            "            {\n" +
            "                \"taskId\": \"TASK_1003\",\n" +
            "                \"formId\": \"FORM_1003\",\n" +
            "                \"taskTitle\": \"厂家串码管理流程\",\n" +
            "                \"taskType\": \"1\",\n" +
            "                \"taskSubType\": \"1\",\n" +
            "                \"createUserName\": \"管理员\",\n" +
            "                \"createTime\": 1547033572000,\n" +
            "                \"taskItemId\": \"ITEM_1003\",\n" +
            "                \"curNodeName\": \"产品初审\",\n" +
            "                \"itemStatus\": \"2\",\n" +
            "                \"assignTime\": 1547033436000\n" +
            "            }\n" +
            "        ],\n" +
            "        \"1020\": [\n" +
            "            {\n" +
            "                \"taskId\": \"TASK_1002\",\n" +
            "                \"formId\": \"FORM_1002\",\n" +
            "                \"taskTitle\": \"厂家固网终端抽检流程\",\n" +
            "                \"taskType\": \"1\",\n" +
            "                \"taskSubType\": \"1020\",\n" +
            "                \"createUserName\": \"张三\",\n" +
            "                \"createTime\": 1546945656000,\n" +
            "                \"taskItemId\": \"ITEM_1002\",\n" +
            "                \"curNodeName\": \"复审\",\n" +
            "                \"itemStatus\": \"1\",\n" +
            "                \"assignTime\": 1546945718000\n" +
            "            }\n" +
            "        ]\n" +
            "    }")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/queryTask")
    @UserLoginToken
    public ResultVO<Map<String, List<TaskPageResp>>> queryTask() {
        TaskPageReq req = new TaskPageReq();
        req.setHandlerUserId(UserContext.getUserId());
        req.setPageSize(100);  //最多查询100条记录

        ResultVO<Page<TaskPageResp>> pageResultVO = taskService.queryTaskPage(req);
        if (!pageResultVO.isSuccess()) {
            return ResultVO.error(pageResultVO.getResultCode(),pageResultVO.getResultMsg());
        }

        //构造前端我的待办展示数据
        Map<String, List<TaskPageResp>> taskPageGroup = genarateTaskListGroup(pageResultVO.getResultData().getRecords());
        return ResultVO.success(taskPageGroup);
    }




    @ApiOperation(value = "通过商家ID获取库存量", notes = "工作台库存信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/getQuantityByMerchantId")
    @UserLoginToken
    public ResultVO<Integer> getQuantityByMerchantId(@RequestParam(value = "merchantId") String merchantId){
        return resourceInstStoreService.getQuantityByMerchantId(merchantId);
    }

    @ApiOperation(value = "根据商家ID获取的订单统计数据", notes = "工作台订单信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/getOrderWorkPlatformShowTotal")
    @UserLoginToken
    public ResultVO<OrderWorkPlatformShowResp> getOrderWorkPlatformShowTotal(@RequestParam(value = "merchantId") String merchantId,
                                                                             @RequestParam(value = "isSupplier") Boolean isSupplier,
                                                                             @RequestParam(value = "lanId",required = false) String lanId){
        OrderStatisticsReq req=new OrderStatisticsReq();
        req.setLanId(lanId);
        req.setIsSupplier(isSupplier);
        req.setMerchantId(merchantId);
        return orderStatisticsService.getOrderWorkPlatformShowTotal(req);
    }

    @ApiOperation(value = "根据商家ID获取产品总量", notes = "工作台产品信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/getProductCountByManufId")
    @UserLoginToken
    public ResultVO<Integer> getProductCountByManufId(@RequestParam(value = "manufacturerId") String manufacturerId){
        ProductCountGetReq req = new ProductCountGetReq();
        req.setManufacturerId(manufacturerId);
        return productService.getProductCountByManufId(req);
    }
    /**
     * 获取我的待办总数
     * @param userId 用户ID
     * @return
     */
    private Long getUnhandledItemCount(String userId) {
        TaskPageReq req = new TaskPageReq();
        req.setHandlerUserId(userId);
        ResultVO<Long> resultVO = taskService.queryTaskCnt(req);
        if (resultVO.isSuccess()) {
            return resultVO.getResultData();
        }
        log.error("call taskService.queryTaskCnt failed,req={},resultVO={}", JSON.toJSONString(req),JSON.toJSONString(resultVO));

        return 0L;
    }

    /**
     * 获取我的申请总数
     * @param userId 用户ID
     * @return
     */
    private Long getAppliedItemCount(String userId) {
        HandleTaskPageReq req = new HandleTaskPageReq();
        req.setCreateUserId(userId);                                    //我创建的
        req.setTaskStatus(WorkFlowConst.TaskState.HANDING.getCode());   //未处理完成的
        ResultVO<Long> resultVO = taskService.queryHandleTaskCnt(req);
        if (resultVO.isSuccess()) {
            return resultVO.getResultData();
        }
        log.error("call taskService.queryTaskCnt failed,req={},resultVO={}", JSON.toJSONString(req),JSON.toJSONString(resultVO));

        return 0L;
    }

    /**
     * 将集合按照任务子类进行分组
     * @param taskPage
     * @return
     */
    private Map<String, List<TaskPageResp>> genarateTaskListGroup(List<TaskPageResp> taskPage) {
        if (taskPage == null) {
            return null;
        }
        log.info("WorkPlatformShowB2BController.genarateTaskListGroup taskPage={}",JSON.toJSONString(taskPage));
        Map<String, List<TaskPageResp>> taskPageGroup = taskPage.stream().collect(Collectors.groupingBy(TaskPageResp::getTaskSubType));
        log.info("WorkPlatformShowB2BController.genarateTaskListGroup taskPageGroup={}",JSON.toJSONString(taskPageGroup));

        return taskPageGroup;
    }


    @ApiOperation(value = "根据商家ID获取绿色通道信息", notes = "工作台绿色通道信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/getGreenChannelLimit")
    @UserLoginToken
    public ResultVO<MerchantLimitDTO> getGreenChannelLimit(@RequestParam(value = "merchantId") String merchantId){
        return merchantLimitService.getMerchantLimit(merchantId);
    }

    @ApiOperation(value = "根据商家ID获取有绿色通道机型", notes = "工作台绿色通道信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "merchantId", value = "merchantId", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", paramType = "query", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", paramType = "query", required = true, dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/getGreenChannelProduct")
    @UserLoginToken
    public ResultVO<Page<ProductDTO>> getGreenChannelProduct(@RequestParam String merchantId,
                                                             @RequestParam Integer pageNo,
                                                             @RequestParam Integer pageSize){
        ResultVO<List<String>> productIdListVO = merchantRulesService.getGreenChannelPermission(merchantId);
        if (!productIdListVO.isSuccess() || CollectionUtils.isEmpty(productIdListVO.getResultData())) {
            return ResultVO.success(new Page<ProductDTO>());
        }
        List<String> productIdList = productIdListVO.getResultData();
        ProductGetReq req = new ProductGetReq();
        req.setProductIdList(productIdList);
        req.setPageNo(pageNo);
        req.setPageSize(pageSize);
        return productService.selectProduct(req);

    }
    
    
    /**
     * 获取用户的业务告警数量
     * @param userId 用户ID
     * @return
     */
    private Long getSysUserMessage(String userId, String messageType) {
        Long count = sysUserMessageService.getSysUserMsgCountByUserIdAndMsgType(userId, messageType);
        return count;
    }
}
