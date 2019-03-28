package com.iwhalecloud.retail.web.controller.b2b.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.req.ActSupDetailReq;
import com.iwhalecloud.retail.promo.dto.req.AddActSupRecordListReq;
import com.iwhalecloud.retail.promo.dto.req.AddActSupReq;
import com.iwhalecloud.retail.promo.dto.req.QueryActSupRecordReq;
import com.iwhalecloud.retail.promo.dto.resp.ActSupDetailResp;
import com.iwhalecloud.retail.promo.dto.resp.ActSupRecodeListResp;
import com.iwhalecloud.retail.promo.service.ActSupDetailService;
import com.iwhalecloud.retail.promo.service.ActSupRecordService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import com.iwhalecloud.retail.workflow.common.ResultCodeEnum;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.TaskItemDTO;
import com.iwhalecloud.retail.workflow.dto.req.RouteNextReq;
import com.iwhalecloud.retail.workflow.dto.req.TaskItemListReq;
import com.iwhalecloud.retail.workflow.service.TaskItemService;
import com.iwhalecloud.retail.workflow.service.TaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author zhou.zc
 * @date 2019年03月06日
 * @Description:前置补贴补录
 */
@RestController
@RequestMapping("/api/b2b/promo/supplementary")
@Slf4j
public class ActSupplementaryB2BController {

    @Reference
    private ActSupRecordService actSupRecordService;

    @Reference
    private ActSupDetailService actSupDetailService;

    @Reference
    private TaskService taskService;

    @Reference
    private TaskItemService taskItemService;

    @ApiOperation(value = "查询前置补贴补录记录", notes = "分页查询")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/queryActSupRecord")
    public ResultVO<Page<ActSupRecodeListResp>> queryActSupRecord(@RequestBody QueryActSupRecordReq queryActSupRecordReq) {
        log.info("ActSupplementaryB2BController queryActSupRecord queryActSupRecordReq={}", JSON.toJSON(queryActSupRecordReq));
        return actSupRecordService.queryActSupRecord(queryActSupRecordReq);
    }

    @ApiOperation(value = "查询前置补贴补录记录详情")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/queryActSupRecordDetail")
    public ResultVO<Page<ActSupDetailResp>> queryActSupRecordDetail(@RequestBody ActSupDetailReq actSupDetailReq) {
        log.info("ActSupplementaryB2BController queryActSupRecordDetail actSupDetailReq={}", JSON.toJSON(actSupDetailReq));
        return actSupDetailService.listActSupDetail(actSupDetailReq);

    }

    @ApiOperation(value = "删除前置补贴补录记录", notes = "更新数据状态")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/deleteActSupRecord")
    public ResultVO deleteActSupRecord(@RequestParam String recordId) {
        log.info("ActSupplementaryB2BController deleteActSupRecord recordId={}", recordId);
        return actSupRecordService.deleteActSupRecord(recordId);
    }

    @PostMapping(value = "/nextRoute")
    public ResultVO nextRoute(@RequestBody TaskItemListReq req) {
        log.info("ActSupplementaryB2BController nextRoute req={} ", JSON.toJSON(req));

        ResultVO<List<TaskItemDTO>> taskItemDTO= taskItemService.queryTaskItem(req);
        if (!taskItemDTO.isSuccess() || CollectionUtils.isEmpty(taskItemDTO.getResultData())) {
            return ResultVO.error(ResultCodeEnum.TASK_ITEM_IS_EMPTY.getDesc());

        }
        RouteNextReq routeNextReq = new RouteNextReq();
        for (TaskItemDTO taskItem:taskItemDTO.getResultData()){
            if (StringUtils.equals(taskItem.getItemStatus(), WorkFlowConst.TASK_ITEM_STATE_PENDING)) {
                routeNextReq.setTaskItemId(taskItem.getTaskItemId());
                break;
            }

        }

        String userId = UserContext.getUserId();
        routeNextReq.setHandlerUserId(userId);
        routeNextReq.setNextNodeId(PromoConst.NEXTNODE_ID);
        routeNextReq.setRouteId(PromoConst.ROUTE_ID);
        if (UserContext.getUser() != null) {
            String userName = UserContext.getUser().getUserName();
            routeNextReq.setHandlerUserName(userName);

        }
        String recordId = req.getFormId();
        String status = PromoConst.ActivitySupStatus.ACTIVITY_SUP_STATUS_PEDING.getCode();

        ResultVO nextRouteResult = taskService.nextRoute(routeNextReq);
        if (nextRouteResult.isSuccess()) {
            return actSupRecordService.updateActSupRecordStatus(recordId, status);

        }
        return nextRouteResult;

    }


    @ApiOperation(value = "新增前置补贴补录记录", notes = "校验订单号和串码")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/addActSupRecord")
    @UserLoginToken
    public ResultVO<List<AddActSupRecordListReq>> addActSupRecord(@RequestBody AddActSupReq addActSupReq) {
        log.info("ActSupplementaryB2BController addActSupRecord addActSupReq={}",JSON.toJSON(addActSupReq));
        addActSupReq.setUserId(UserContext.getUserId());
        UserDTO userDTO = UserContext.getUser();
        if (!Objects.isNull(userDTO)) {
            addActSupReq.setUserName(userDTO.getUserName());
        }

        return actSupRecordService.addActSup(addActSupReq);
    }
}
