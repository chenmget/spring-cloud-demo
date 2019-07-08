package com.iwhalecloud.retail.web.controller.b2b.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wenlong.zhong
 * @date 2019/4/2
 */

@RestController
@RequestMapping("/api/commonRegion")
@Slf4j
@Api(value="本地网区域", tags={"本地网区域"})
public class CommonRegionController {

    @Reference
    private CommonRegionService commonRegionService;

    @ApiOperation(value = "查询本地网区域列表", notes = "不传参数，默认查湖南 本地网 列表")
    @ApiImplicitParam(
            name = "parRegionId", value = "父级区域ID，为空默认查湖南本地网列表", paramType = "query", required = false, dataType = "String"
    )
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/list")
    public ResultVO<List<CommonRegionDTO>> listCommonRegion(@RequestParam(required = false, value = "parRegionId") String parRegionId){
        CommonRegionListReq req = new CommonRegionListReq();
        if (!StringUtils.isEmpty(parRegionId)) {
            req.setParRegionId(parRegionId);
        }
        return commonRegionService.listCommonRegion(req);
    }

    @ApiOperation(value = "查询湖南的本地网列表", notes = "查询湖南的本地网列表")
    @ApiImplicitParam(
            name = "parRegionId", value = "父级区域ID，为空默认查湖南本地网列表", paramType = "query", required = false, dataType = "String"
    )
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/listLan")
    public ResultVO<List<CommonRegionDTO>> listLan(){
        return commonRegionService.listLan();
    }

}
