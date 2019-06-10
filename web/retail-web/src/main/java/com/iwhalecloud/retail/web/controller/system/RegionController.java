package com.iwhalecloud.retail.web.controller.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.dto.request.RegionsListReq;
import com.iwhalecloud.retail.system.dto.response.RegionsGetResp;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.system.service.RegionsService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author he.sw
 * @Date 2018/12/01
 **/
@RestController
@RequestMapping("/api/region")
@Slf4j
public class RegionController {

	@Reference
    private RegionsService regionService;

    @Reference
    private CommonRegionService commonRegionService;


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

    @ApiOperation(value = "查询本地网区域列表", notes = "不传参数，默认查湖南 本地网 列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name="parRegionId", value = "父级区域ID，为空默认查湖南本地网列表", paramType = "query", required = false, dataType = "String"),
        @ApiImplicitParam(name = "levelFlag", value = "查询层级", paramType = "query", required = false, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/listCommonRegion")
    public ResultVO<List<CommonRegionDTO>> listCommonRegion(@RequestParam(required = false, value = "parRegionId") String parRegionId,
                                                            @RequestParam(required = false, value = "levelFlag") String levelFlag){
        CommonRegionListReq req = new CommonRegionListReq();
        if (!StringUtils.isEmpty(parRegionId)) {
            req.setParRegionId(parRegionId);
            if(StringUtils.isEmpty(levelFlag)){
                //没有传入查询标记时，如果当前等级是30(地市公司级)，无需查询下一级
                ResultVO<CommonRegionDTO> resultVO = commonRegionService.getCommonRegionById(parRegionId);
                CommonRegionDTO commonRegionDTO = resultVO.getResultData();
                if(SystemConst.REGION_LEVEL.LEVEL_30.getCode().equals(commonRegionDTO.getRegionLevel())){
                    return ResultVO.success();
                }
            }
        }

        return commonRegionService.listCommonRegion(req);
    }

}
