package com.iwhalecloud.retail.web.controller.cloud;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.CloudDeviceLogDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudDeviceLogReq;
import com.iwhalecloud.retail.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.oms.service.CloudDeviceLogService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/cloudDeviceLog")
public class CloudDeviceLogController extends BaseController {
	
    @Reference
    private CloudDeviceLogService cloudDeviceLogService;


    @ApiOperation(value = "更新云货架设备信息", notes = "根据设备唯一的编号、首次连接时间（创建时间）、当前时间（修改时间）")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/addCloudDeviceLog", method = RequestMethod.POST)
    public ResultVO addCloudDeviceLog(@RequestBody CloudDeviceLogDTO cloudDeviceLogDTO) throws  Exception{
        return cloudDeviceLogService.addCloudDeviceLog(cloudDeviceLogDTO);
    }


    @ApiOperation(value = "根据厅店ID/地市/城区统计设备数量及每个设备的工作时长", notes = "根据厅店ID/地市/城区统计设备数量及每个设备的工作时长")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryDeviceNumberWorkTime", method = RequestMethod.POST)
    public ResultVO queryDeviceNumberWorkTime(@RequestBody CloudDeviceLogReq cloudDeviceLogReq) throws  Exception{
        return cloudDeviceLogService.queryDeviceNumberWorkTime(cloudDeviceLogReq);
    }

}