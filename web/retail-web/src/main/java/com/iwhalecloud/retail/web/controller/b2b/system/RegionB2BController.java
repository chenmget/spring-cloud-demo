package com.iwhalecloud.retail.web.controller.b2b.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.request.RegionsListReq;
import com.iwhalecloud.retail.system.dto.response.RegionsGetResp;
import com.iwhalecloud.retail.system.service.RegionsService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author he.sw
 * @Date 2018/12/26
 **/
@RestController
@RequestMapping("/api/b2b/region")
@Slf4j
public class RegionB2BController {

	@Reference
    private RegionsService regionService;


    @GetMapping(value="listRegions")
    @ApiOperation(value = "查询省、市、区列表", notes = "查询省、市、区列表，可根据父级获取子级列表或者查询省级区域。")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "regionGrades", value = "区域类型，参数值为regionGrade，查询省市区列表，优先按regionGrade条件查询", paramType = "query", required = false, dataType = "String"),
        @ApiImplicitParam(name = "regionParentId", value = "区域父级ID，如果不传且区域类型为空则默认为湖南省", paramType = "query", required = false, dataType = "String")
    })
    @ApiResponses({
        @ApiResponse(code=400,message="请求参数不全"),
        @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    public ResultVO<List<RegionsGetResp>> listRegions(@RequestParam(value = "regionGrades", required = false) List<String> regionGrades,
    		@RequestParam(value = "regionParentId", required = false) String regionParentId) {
        RegionsListReq req = new RegionsListReq();
        if(null != regionGrades && !regionGrades.isEmpty()){
            req.setRegionGrades(regionGrades);
        }
        if(StringUtils.isNotEmpty(regionParentId)){
            req.setRegionParentId(regionParentId);
        } else {
            // 默认查询湖南
        	req.setRegionParentId("430000");
        }
        return regionService.listRegions(req);
        
    }

    @ApiOperation(value = "查询区域父级ID", notes = "传入区域ID查询父级ID")
    @ApiImplicitParam(name = "regionsId", value = "regionsId", paramType = "query", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/getPregionsId", method = RequestMethod.GET)
    public ResultVO<RegionsGetResp> getPregionsId(@RequestParam String regionsId){
        if(StringUtils.isEmpty(regionsId)){
            return ResultVO.error("regionsId must be not null");
        }
        return regionService.getPregionId(regionsId);
   
    }



    @ApiOperation(value = "查询区域集合", notes = "传入id集合查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="getRegionList")
    public ResultVO<List<RegionsGetResp>> getRegionList(@RequestParam(value = "idList") @ApiParam(value = "id列表", required = true) List<String> idList) {
        if(null == idList || idList.isEmpty()){
            return ResultVO.error("regionsId must be not null");
        }
        return regionService.getRegionList(idList);
    }
}
