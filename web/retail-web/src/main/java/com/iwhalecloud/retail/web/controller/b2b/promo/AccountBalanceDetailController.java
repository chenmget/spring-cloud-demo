package com.iwhalecloud.retail.web.controller.b2b.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityByMerchantListReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceDetailAllReq;
import com.iwhalecloud.retail.promo.dto.resp.MarketingActivityByMerchantResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceDetailAllResp;
import com.iwhalecloud.retail.promo.dto.resp.UserDTO;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.promo.service.AccountBalanceDetailService;

@Slf4j
@RestController
@RequestMapping("/api/b2b/promo/accountBalanceDetail")
@Api(value="账户余额来源明细:", tags={"AccountBalanceDetailController"})
public class AccountBalanceDetailController {
	
    @Reference
    private AccountBalanceDetailService accountBalanceDetailService;


    @ApiOperation(value = "返利收入明细查询", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/queryAccountBalanceDetailAllForPage")
    @UserLoginToken
    public ResultVO<Page<QueryAccountBalanceDetailAllResp>> queryAccountBalanceDetailAllForPage(@RequestBody QueryAccountBalanceDetailAllReq req){
        log.info("AccountBalanceDetailController queryAccountBalanceDetailAllForPage QueryAccountBalanceDetailAllReq={} ", req);
        req.setCustId(UserContext.getMerchantId());
        return accountBalanceDetailService.queryAccountBalanceDetailAllForPage(req);
    }




}