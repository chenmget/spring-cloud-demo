package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstsTrackDetailGetReq;
import com.iwhalecloud.retail.warehouse.service.ResouceInstTrackDetailService;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/b2b/trackDetail")
public class ResourceInstTrackDetailB2BController {

    @Reference
    private ResouceInstTrackDetailService resouceInstTrackDetailService;

    @ApiOperation(value = "查询串码轨迹明细", notes = "查询串码轨迹明细")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "getResourceInstTrackDetailByNbr")
    public ResultVO getResourceInstTrackDetailByNbr(@RequestParam(value = "nbr", required = true) String nbr) {
        ResourceInstsTrackDetailGetReq req = new ResourceInstsTrackDetailGetReq();
        if (UserContext.isUserLogin()) {
            req.setTargetMerchantId(UserContext.getMerchantId());
        }
        req.setMktResInstNbr(nbr);
        return resouceInstTrackDetailService.getResourceInstTrackDetailByNbr(req);
    }


}
