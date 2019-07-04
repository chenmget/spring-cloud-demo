package com.iwhalecloud.retail.web.controller.b2b.purchase;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyDeliveryResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyDeliveryReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyExtReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReceivingReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.service.PurchaseApplyService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/4/24 10:05
 * @description 采购管理发货收货
 */

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/purchase")
public class PurchaseApplyController extends BaseController {

    @Reference
    private PurchaseApplyService purchaseApplyService;

    @ApiOperation(value = "采购单发货", notes = "采购单发货")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @UserLoginToken
    @PostMapping("/delivery")
    public ResultVO delivery(@RequestBody PurApplyDeliveryReq req) {
        if (null != UserContext.getUser()) {
            req.setRegionId(UserContext.getUser().getRegionId());
            req.setCityId(UserContext.getUser().getLanId());
        }
        req.setUserId(UserContext.getUserId());
        req.setCreateStaff(UserContext.getUserId());
        ResultVO resultVO = purchaseApplyService.delivery(req);
        if (resultVO.isSuccess()) {
            return ResultVO.successMessage("发货成功");
        }
        return ResultVO.error(resultVO.getResultMsg());
    }

    @ApiOperation(value = "采购单确认收货", notes = "采购单确认收货")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @UserLoginToken
    @PostMapping("/receiving")
    public ResultVO receiving(@RequestBody @Valid PurApplyReceivingReq req) {
        String userId = UserContext.getUserId();
        req.setCreateStaff(userId);
        if (null != UserContext.getUser()) {
            req.setRegionId(UserContext.getUser().getRegionId());
            req.setLanId(UserContext.getUser().getLanId());
            if (UserContext.getUser().getLanId()==null) {
                return ResultVO.error("用户地市信息为空");
            }
        }else {
            return ResultVO.error("获取用户信息失败");
        }
//        req.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
//        req.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
//        req.setSourceType(ResourceConst.SOURCE_TYPE.SUPPLIER.getCode());
//        req.setStorageType(ResourceConst.STORAGETYPE.SUPPLIER_INPUT.getCode());
//        req.setMktResInstType(ResourceConst.MKTResInstType.TRANSACTION.getCode());
        //req.setMerchantId(UserContext.getMerchantId());
        log.info("UserContext.getMerchantId()="+UserContext.getMerchantId());
        ResultVO resultVO = purchaseApplyService.receiving(req);
        if (resultVO.isSuccess()) {
            return ResultVO.successMessage("确认收货成功");
        }
        return ResultVO.error(resultVO.getResultMsg());
    }

    @ApiOperation(value = "添加收货地址", notes = "添加收货地址")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @UserLoginToken
    @PostMapping("/addReceiveAddress")
    public ResultVO addReceiveAddress(@RequestBody PurApplyExtReq req) {
        String userId = UserContext.getUserId();
        req.setCreateStaff(userId);
        ResultVO resultVO = purchaseApplyService.addPurApplyExtInfo(req);
        if (resultVO.isSuccess()) {
            return ResultVO.successMessage("添加收货地址成功");
        }
        return ResultVO.error(resultVO.getResultMsg());
    }

    @ApiOperation(value = "更新收货地址", notes = "更新收货地址")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @UserLoginToken
    @PostMapping("/updateReceiveAddress")
    public ResultVO updateReceiveAddress(@RequestBody PurApplyExtReq req) {
        String userId = UserContext.getUserId();
        req.setUpdateStaff(userId);
        ResultVO resultVO = purchaseApplyService.updatePurApplyExtInfo(req);
        if (resultVO.isSuccess()) {
            return ResultVO.successMessage("更新收货地址成功");
        }
        return ResultVO.error(resultVO.getResultMsg());
    }


    @PostMapping("/getDeliveryInfoByApplyID")
    @UserLoginToken
    public ResultVO<Page<PurApplyDeliveryResp>> getDeliveryInfoByApplyID(@RequestBody PurApplyReq req) {

//        String userId = UserContext.getUserId();
//		String userId = "100028487";

//        发货信息
        ResultVO<Page<PurApplyDeliveryResp>> DeliveryItemInfo =purchaseApplyService.getDeliveryInfoByApplyID(req);

        return DeliveryItemInfo;
    }

}

