package com.iwhalecloud.retail.web.controller.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountListReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountSaveReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountUpdateReq;
import com.iwhalecloud.retail.partner.service.MerchantAccountService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wenlong.zhong
 * @date 2019/3/20
 */

@Slf4j
@RestController
@RequestMapping("/api/merchantAccount")
public class MerchantAccountController {
    @Reference
    private MerchantAccountService merchantAccountService;

    @ApiOperation(value = "商家账户新增接口", notes = "商家账户新增接口")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultVO<MerchantAccountDTO> saveAccount(@RequestBody @ApiParam(value = "商家账户新增", required = true) MerchantAccountSaveReq req) {
        return merchantAccountService.saveMerchantAccount(req);
    }

//    @ApiOperation(value = "根据ACCOUNT_ID查询", notes = "根据ACCOUNT_ID查询")
//    @ApiResponses({
//            @ApiResponse(code = 400, message = "请求参数没填好"),
//            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
//    })
//    @RequestMapping(value = "/get", method = RequestMethod.POST)
//    public ResultVO<MerchantAccountDTO> getMerchantAccountById(@RequestParam(value = "accountId") String accountId) {
//        return merchantAccountService.getMerchantAccountById(accountId);
//    }

    @ApiOperation(value = "商家账户更新接口", notes = "商家账户更新接口")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVO updateAccount(@RequestBody @ApiParam(value = "商家账户更新", required = true) MerchantAccountUpdateReq req) {
        return merchantAccountService.updateMerchantAccount(req);
    }

    @ApiOperation(value = "商家账户删除接口", notes = "商家账户删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountId", value = "商家账户ID", paramType = "query", required = true, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultVO deleteAccount(@RequestParam(value = "accountId") String accountId) {
        return merchantAccountService.deleteMerchantAccountById(accountId);
    }

    @ApiOperation(value = "商家账户列表接口", notes = "商家账户列表接口")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultVO<List<MerchantAccountDTO>> listAccount(@RequestBody @ApiParam(value = "商家账户列表", required = true) MerchantAccountListReq req) {
        return merchantAccountService.listMerchantAccount(req);
    }

}
