package com.iwhalecloud.retail.web.controller.b2b.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.req.AddAccountReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountForPageReq;
import com.iwhalecloud.retail.promo.dto.req.QueryTotalAccountReq;
import com.iwhalecloud.retail.promo.dto.req.UpdateAccountReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountForPageResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryTotalAccountResp;
import com.iwhalecloud.retail.promo.service.AccountService;
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
@RequestMapping("/api/b2b/promo/account")
@Api(value="账户:", tags={"AccountController"})
public class AccountController {
	
    @Reference
    private AccountService accountService;


    @ApiOperation(value = "账户总额查询服务", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/queryTotalAccount")
    @UserLoginToken
    public ResultVO<Page<QueryTotalAccountResp>> queryTotalAccount(@RequestBody QueryTotalAccountReq req){
        log.info("AccountController queryTotalAccount queryTotalAccountReq={} ", req);
        req.setCustId(UserContext.getMerchantId());
        return accountService.queryTotalAccount(req);

    }
    @ApiOperation(value = "分页查询账户", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/queryAccountForPage")

    public ResultVO<Page<QueryAccountForPageResp>> queryAccountForPage(@RequestBody QueryAccountForPageReq req){
        req.setCustId(UserContext.getMerchantId());
        log.info("AccountController queryAccountForPage QueryAccountForPageReq={} ", req);
        return accountService.queryAccountForPage(req);

    }
    @ApiOperation(value = "添加账户", notes = "添加账户")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/addAccount")
    @UserLoginToken
    public ResultVO addAccount(@RequestBody AddAccountReq req){
        log.info("AccountController addAccount AddAccountReq={} ", req);
        req.setCreateStaff(UserContext.getUserId());
        return accountService.addAccount(req);

    }
    @ApiOperation(value = "修改账户", notes = "修改账户")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/updateAccount")
    @UserLoginToken
    public ResultVO updateAccount(@RequestBody UpdateAccountReq req){
        log.info("AccountController updateAccount UpdateAccountReq={} ", req);
        req.setUpdateStaff(UserContext.getUserId());
        return accountService.updateAccount(req);

    }
    
    
}