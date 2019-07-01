package com.iwhalecloud.retail.web.controller.b2b.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.SupplyProductRelDTO;
import com.iwhalecloud.retail.partner.exception.BaseException;
import com.iwhalecloud.retail.partner.service.SupplyProductRelService;
import com.iwhalecloud.retail.warehouse.common.ResultCodeEnum;
import com.iwhalecloud.retail.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/supplyProduct")
public class SupplyProductController extends BaseController {

    @Reference
    private SupplyProductRelService supplyProductRelService;

    @ApiOperation(value = "绑定可供产品", notes = "根据supplierId、productId，绑定可供产品")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //绑定可供产品
    @RequestMapping(value = "/bindSupplyProduct", method = RequestMethod.POST)
    public ResultVO bindSupplyProduct(@RequestBody @ApiParam(value = "查询条件", required = true) List<SupplyProductRelDTO> supplyList) {
        ResultVO resultVO = new ResultVO<>();
        for (int i = 0; i < supplyList.size(); i++) {
            supplyProductRelService.bindSupplyProductRel(supplyList.get(i));
        }
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultData("绑定成功");
        resultVO.setResultMsg("成功");
        return resultVO;
    }

    @ApiOperation(value = "解除绑定可供产品", notes = "根据relId，解除绑定可供产品")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //解除绑定可供产品
    @RequestMapping(value = "/unBindSupplyProduct", method = RequestMethod.DELETE)
    public ResultVO unBindSupplyProduct(@RequestBody @ApiParam(value = "查询条件", required = true) List<SupplyProductRelDTO> supplyList) throws ParseException, BaseException {
        ResultVO resultVO = new ResultVO<>();
        for (int i = 0; i < supplyList.size(); i++) {
            supplyProductRelService.unBindSupplyProductRel(supplyList.get(i));
        }
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultData("解除绑定成功");
        resultVO.setResultMsg("成功");
        return resultVO;
    }
}