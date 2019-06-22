package com.iwhalecloud.retail.web.controller.cms;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.ContentChkhisDTO;
import com.iwhalecloud.retail.oms.service.ContentChkhisService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/contentChkhis")
public class ContentChkhisController {
	
	@Reference
    private ContentChkhisService contentChkhisService;

    /**
     * 审核服务
     * 入参：传内容ID、审核人员工号、审核结果、审核意见
     * 逻辑应该要改下：
     * 先判断内容状态是不是待审核，如果是待审核，再去更新审核历史。同时内容表中那条内容记录要加锁。更新完审核历史后，再更新内容状态。
     * @param contentChkhis
     * @return
     */
    @ApiOperation(value = "内容审核", notes = "内容审核")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/contentCheck", method = RequestMethod.POST)
    public ResultVO contentCheck(@RequestBody @ApiParam( value = "内容ID", required = true ) ContentChkhisDTO contentChkhis) {
        return contentChkhisService.contentCheck(contentChkhis);
    }
    
    
}