package com.iwhalecloud.retail.web.controller.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.SupplierDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplierListReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierQueryReq;
import com.iwhalecloud.retail.partner.service.SupplierService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zwl
 * @date 2018/10/29
 */
@RestController
@RequestMapping("/api/supplier")
@Slf4j
public class SupplierController extends BaseController {

    @Reference
    private SupplierService supplierService;

    @Reference
    private UserService userService;


    @GetMapping("/listSupplier")
    public ResultVO<List<SupplierDTO>> listSupplier() {
        SupplierListReq req = new SupplierListReq();
        return supplierService.listSupplier(req);
    }

    @ApiOperation(value = "供应商查询列表", notes = "供应商查询列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    ResultVO<List<SupplierDTO>> supplierList(@RequestBody @ApiParam( value = "supplierListReq", required = true ) SupplierListReq supplierListReq){
        //1、先查供应商列表
//        List<SupplierDTO> result = supplierService.listSupplier(supplierListReq);
        //2、 获取供应商对应的 系统账号 登录名
//        for (SupplierDTO supplierDTO : result.getRecords()){
//            if (!StringUtils.isEmpty(supplierDTO.getUserId())){
//                UserDTO userDTO = userService.getUserByUserId(supplierDTO.getUserId());
//                if (userDTO != null){
//                    supplierDTO.setLoginName(userDTO.getLoginName());
//                }
//            }
//        }
        return supplierService.listSupplier(supplierListReq);
    }


    @ApiOperation(value = "供应商分页查询", notes = "供应商查询返回信息分页")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/pageSupplier", method = RequestMethod.POST)
    ResultVO<Page<SupplierDTO>> pageSupplier(@RequestBody @ApiParam( value = "supplierQueryReq", required = true ) SupplierQueryReq supplierQueryReq){
        //1、先查供应商列表
        Page<SupplierDTO> result = supplierService.pageSupplier(supplierQueryReq);

        //2、 获取供应商对应的 系统账号 登录名
        for (SupplierDTO supplierDTO : result.getRecords()){
            if (!StringUtils.isEmpty(supplierDTO.getUserId())){
                UserDTO userDTO = userService.getUserByUserId(supplierDTO.getUserId());
                if (userDTO != null){
                    supplierDTO.setLoginName(userDTO.getLoginName());
                }
            }
        }
        return  successResultVO(result);
    }

}
