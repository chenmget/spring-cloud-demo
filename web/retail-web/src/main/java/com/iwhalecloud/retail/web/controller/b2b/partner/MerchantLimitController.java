package com.iwhalecloud.retail.web.controller.b2b.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantLimitDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantLimitSaveReq;
import com.iwhalecloud.retail.partner.service.MerchantLimitService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author wenlong.zhong
 * @date 2019/3/20
 */

@Slf4j
@RestController
@RequestMapping("/api/merchantLimit")
public class MerchantLimitController {

    @Reference
    private MerchantLimitService merchantLimitService;


    @ApiOperation(value = "商家限额新增接口", notes = "商家限额新增接口")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultVO<Integer> saveMerchantLimit(@RequestBody @ApiParam(value = "商家限额新增", required = true) MerchantLimitSaveReq req) {
        return merchantLimitService.saveMerchantLimit(req);
    }

    @ApiOperation(value = "根据商家ID查询限额", notes = "根据商家ID查询限额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "merchantId", value = "商家ID", paramType = "query", required = true, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultVO<MerchantLimitDTO> getMerchantAccountById(@RequestParam(value = "merchantId") String merchantId) {

        return merchantLimitService.getMerchantLimit(merchantId);
    }

}
