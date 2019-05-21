package com.iwhalecloud.retail.web.controller.b2b.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import com.iwhalecloud.retail.system.dto.SysUserMessageDTO;
import com.iwhalecloud.retail.system.dto.request.SysUserMessageReq;
import com.iwhalecloud.retail.system.service.SysUserMessageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/sysUserMessage")
public class SysUserMessageController {
	
    @Reference
    private SysUserMessageService sysUserMessageService;

    @Reference
    private MarketingActivityService marketingActivityService;


    @ApiOperation(value = "查询营销活动未发货记录带分页")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/queryWarnUserMessageList")
    public ResultVO<IPage<SysUserMessageDTO>> queryActSupRecordDetail(@RequestBody SysUserMessageReq sysUserMessageReq) {
        log.info("SysUserMessageController queryWarnUserMessageList actSupDetailReq={}", JSON.toJSON(sysUserMessageReq));
        if(Objects.isNull(sysUserMessageReq)) {
            return ResultVO.successMessage("user message is null");
        }
        IPage<SysUserMessageDTO> page = sysUserMessageService.selectWarnMessageList(sysUserMessageReq);
        return ResultVO.success(page);

    }
    
    

    @PostMapping(value = "/notifyMerchantActivityOrderDelivery")
    public ResultVO<IPage<SysUserMessageDTO>> notifyMerchantActivityOrderDelivery() {
        marketingActivityService.notifyMerchantActivityOrderDelivery();
     return ResultVO.success();

    }

    @PostMapping(value = "/updateSysUserMessage")
    public ResultVO updateSysUserMessage() {
        sysUserMessageService.updateSysUserMessage();
        return ResultVO.success();

    }
    
    @ApiOperation(value = "修改消息状态为已读")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/updateSysReadFlag")
    public ResultVO updateSysReadFlag(@RequestBody SysUserMessageReq sysUserMessageReq) {
        log.info("SysUserMessageController updateSysReadFlag actSupDetailReq={}", JSON.toJSON(sysUserMessageReq));
        if(Objects.isNull(sysUserMessageReq)) {
            return ResultVO.successMessage("user message is null");
        }
    	sysUserMessageService.updateReadFlagByUserId(sysUserMessageReq.getUserId());
        return ResultVO.success();
    }
    
    @ApiOperation(value = "获取所有未读消息")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/getSysMessageCount")
    public ResultVO getSysMessageCount(@RequestBody SysUserMessageReq sysUserMessageReq) {
        log.info("SysUserMessageController updateSysReadFlag actSupDetailReq={}", JSON.toJSON(sysUserMessageReq));
        if(Objects.isNull(sysUserMessageReq)) {
            return ResultVO.successMessage("user message is null");
        }
        Long count = sysUserMessageService.getSysMsgNotReadAcount(sysUserMessageReq.getUserId());
        Map<String, Long> countMap = new HashMap<String, Long>();
        countMap.put("sysUserMsgCount", count);
        return ResultVO.success(countMap);
    }


}