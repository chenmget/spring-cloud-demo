package com.iwhalecloud.retail.web.controller.b2b.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceAllReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceAllResp;
import com.iwhalecloud.retail.promo.service.AccountBalanceService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/b2b/promo/accountBalance")
@Api(value="返利账本:", tags={"AccountBalanceController"})
public class AccountBalanceController {
	
    @Reference
    private AccountBalanceService accountBalanceService;


    @ApiOperation(value = "返利余额查询", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/queryAccountBalanceAllForPage")
    @UserLoginToken
    public ResultVO<Page<QueryAccountBalanceAllResp>> queryAccountBalanceAllForPage(@RequestBody QueryAccountBalanceAllReq req){
        log.info("AccountBalanceController queryAccountBalanceAllForPage QueryAccountBalanceAllReq={} ", req);
        req.setCustId(UserContext.getMerchantId());
        return accountBalanceService.queryAccountBalanceAllForPage(req);
    }

}