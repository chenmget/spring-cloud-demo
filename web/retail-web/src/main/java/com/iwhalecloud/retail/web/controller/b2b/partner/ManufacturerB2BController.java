package com.iwhalecloud.retail.web.controller.b2b.partner;


import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.ManufacturerDTO;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerPageReq;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerSaveReq;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerUpdateReq;
import com.iwhalecloud.retail.partner.service.ManufacturerService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zhong.wenlong
 * @Date 2018/12/27
 *
 * 厂商信息
 **/
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/b2b/manufacturer")
public class ManufacturerB2BController {

    @Reference
    private ManufacturerService manufacturerService;

    @ApiOperation(value = "厂商信息新建接口", notes = "厂商信息新建接口")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/save")
    public ResultVO<ManufacturerDTO> saveManufacturer(@RequestBody @ApiParam(value = "厂商信息新建入参", required = true) ManufacturerSaveReq manufacturerSaveReq){
        log.info("ManufacturerB2BController.saveManufacturer(), 入参manufacturerSaveReq={} ", manufacturerSaveReq);
        ResultVO resp = manufacturerService.saveManufacturer(manufacturerSaveReq);
        log.info("ManufacturerB2BController.saveManufacturer(), 出参resp={} ",resp);
        return resp;
    }

    @ApiOperation(value = "厂商信息更新接口", notes = "厂商信息更新接口。")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value="/update")
    public ResultVO<Integer> updateManufacturer(
            @RequestBody @ApiParam(value = "厂商信息更新入参", required = true) ManufacturerUpdateReq manufacturerUpdateReq
    ) {
        log.info("ManufacturerB2BController.updateManufacturer(), manufacturerUpdateReq={} ",manufacturerUpdateReq);
        ResultVO resp = manufacturerService.updateManufacturer(manufacturerUpdateReq);
        log.info("ManufacturerB2BController.updateManufacturer(), 出参resp={} ",resp);
        return resp;
    }

    @ApiOperation(value = "厂商信息删除接口", notes = "厂商信息删除接口。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "manufacturerId", value = "厂商ID", paramType = "query", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value="/delete")
    public ResultVO deleteManufacture(@RequestParam(value = "manufacturerId") String manufacturerId) {
        log.info("ManufacturerB2BController.deleteManufacture(), 入参manufacturerId={} ",manufacturerId);
        if(StringUtils.isEmpty(manufacturerId)){
            return ResultVO.error("厂商ID不能为空");
        }
        ResultVO resp = manufacturerService.deleteManufacturerById(manufacturerId);
        log.info("ManufacturerB2BController.deleteManufacture(), 出参resp={} ",resp);
        return resp;
    }

    @ApiOperation(value = "查询厂商详情接口", notes = "根据ID查询厂商信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "manufacturerId", value = "厂商ID", paramType = "query", required = true, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/get")
    public ResultVO<ManufacturerDTO> getManufacturer(@RequestParam(value = "manufacturerId") String manufacturerId) {
        log.info("ManufacturerB2BController.getManufacturer(), 入参manufacturerId={} ",manufacturerId);
        if(StringUtils.isEmpty(manufacturerId)){
            return ResultVO.error("厂商ID不能为空");
        }
        ResultVO resp = manufacturerService.getManufacturerById(manufacturerId);
        log.info("ManufacturerB2BController.getManufacturer(), 出参resp={} ",resp);
        return resp;
    }

    @ApiOperation(value = "厂商信息分页列表查询接口", notes = "厂商信息分页列表查询，名称和编码都是模糊查询。")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/page")
    public ResultVO pageManufacturer(@RequestBody @ApiParam(value = "厂商信息分页入参", required = true) ManufacturerPageReq manufacturerPageReq){
        log.info("ManufacturerB2BController.pageManufacturer(), 入参manufacturerPageReq={} ",manufacturerPageReq);
        ResultVO resp = manufacturerService.pageManufacturer(manufacturerPageReq);
        log.info("ManufacturerB2BController.pageManufacturer(), 出参resp={} ",resp);
        return resp;
    }
}
