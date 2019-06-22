package com.iwhalecloud.retail.web.controller.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.SupplierAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountAddReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountQueryReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierAccountUpdateReq;
import com.iwhalecloud.retail.partner.dto.resp.SupplierAccountAddResp;
import com.iwhalecloud.retail.partner.service.SupplierAccountService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/supplierAccount")
public class SupplierAccountController {
	
    @Reference
    private SupplierAccountService supplierAccountService;


    @ApiOperation(value = "供应商账户新增", notes = "供应商账户新增接口")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/createSupplierAccount", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<SupplierAccountAddResp> createSupplierAccount(@RequestBody @ApiParam( value = "供应商账户新增", required = true ) SupplierAccountAddReq request) {
        if (StringUtils.isEmpty(request.getSupplierId())) {
            return ResultVO.error("供应商ID不能为空！");
        }
        if (StringUtils.isEmpty(request.getAccountType())) {
            return ResultVO.error("账户类型不能为空！");
        }
        if (StringUtils.isEmpty(request.getAccount())) {
            return ResultVO.error("账户号不能为空！");
        }
        try {
            SupplierAccountAddResp resp = supplierAccountService.createSupplierAccount(request);
            if (resp == null) {
                return ResultVO.error("供应商账户新增信息失败");
            }
            return ResultVO.success(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(e.getMessage());
        }
    }

    @ApiOperation(value = "供应商账户修改、删除", notes = "供应商账户修改、删除接口")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/updateSupplierAccount", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Integer> updateSupplierAccount(@RequestBody @ApiParam( value = "供应商账户修改、删除", required = true )SupplierAccountUpdateReq request) {
        if (StringUtils.isEmpty(request.getSupplierId())) {
            return ResultVO.error("供应商ID不能为空！");
        }
        if (StringUtils.isEmpty(request.getAccountType())) {
            return ResultVO.error("账户类型不能为空！");
        }
        if (StringUtils.isEmpty(request.getAccount())) {
            return ResultVO.error("账户号不能为空！");
        }
        if (StringUtils.isEmpty(request.getState())) {
            return ResultVO.error("状态不能为空！");
        }
        try {
            int result = supplierAccountService.editSupplierAccount(request);
            if (result == 0) {
                return ResultVO.error("删除员工信息失败");
            }
            return ResultVO.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(e.getMessage());
        }
    }

    @ApiOperation(value = "供应商账号查询", notes = "供应商账号查询返回信息分页")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/querySupplierAccount", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Page<SupplierAccountDTO>> querySupplierAccount(@RequestBody @ApiParam( value = "supplierQueryReq", required = true ) SupplierAccountQueryReq supplierAccountQueryReq)throws Exception{
        ResultVO<Page<SupplierAccountDTO>> pageResultVO = new ResultVO<Page<SupplierAccountDTO>>();
        Page<SupplierAccountDTO> supplierAccountDTOPage = new Page<SupplierAccountDTO>();
        try {
            supplierAccountDTOPage = supplierAccountService.querySupplierAccount(supplierAccountQueryReq);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(e.getMessage());
        }
        return ResultVO.success(supplierAccountDTOPage);
    }
}