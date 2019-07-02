package com.iwhalecloud.retail.web.controller.b2b.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.ResultCodeEnum;
import com.iwhalecloud.retail.partner.dto.PartnerRelShopDTO;
import com.iwhalecloud.retail.partner.dto.PartnerShopDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.service.PartnerShopService;
import com.iwhalecloud.retail.web.controller.BaseController;
import io.swagger.annotations.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/partnerShop")
public class PartnerShopController extends BaseController<Object> {
	
    @Reference
    private PartnerShopService partnerShopService;


    @ApiOperation(value = "查询店铺信息", notes = "根据店铺ID查询店铺信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partnerShopId", value = "partnerShopId", paramType = "path", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/get/{partnerShopId}", method = RequestMethod.GET)
    public ResultVO<PartnerShopDTO> getPartnerShop(@PathVariable String partnerShopId) {
        ResultVO<PartnerShopDTO> resultVO = new ResultVO<PartnerShopDTO>();
        PartnerShopDTO dto = partnerShopService.getPartnerShop(partnerShopId);
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        
        resultVO.setResultData(dto);
        resultVO.setResultMsg("成功");
        return resultVO;
    }

    @ApiOperation(value = "修改店铺信息", notes = "根据partnerId修改店铺信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResultVO<Integer> updatePartnerShopDTO(@RequestBody PartnerShopUpdateReq req) {
    	if (StringUtils.isEmpty(req.getName())) {
    		return ResultVO.error("厅店名称不能为空！");
    	}
    	if (StringUtils.isEmpty(req.getNetType())) {
    		return ResultVO.error("厅店类型不能为空！");
    	}
//    	if (StringUtils.isEmpty(req.getPartnerId())) {
//    		return ResultVO.error("厅店ID不能为空！");
//    	}
        if (StringUtils.isEmpty(req.getPartnerShopId())) {
            return ResultVO.error("厅店ID不能为空！");
        }
    	
        ResultVO<Integer> resultVO = new ResultVO<Integer>();
        int resp = partnerShopService.updatePartnerShop(req);
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultData(resp);
        resultVO.setResultMsg("成功");
        return resultVO;
    }
    
    @ApiOperation(value = "修改店铺状态", notes = "根据分销商ID修改店铺状态")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/updateState", method = RequestMethod.PUT)
    public ResultVO<Integer> updatePartnerShopStateO(@RequestBody PartnerShopStateUpdateReq req) {
        if (StringUtils.isEmpty(req.getPartnerShopId())) {
            return ResultVO.error("厅店ID不能为空！");
        }
        ResultVO<Integer> resultVO = new ResultVO<Integer>();
        int resp = partnerShopService.updatePartnerShopState(req);
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultData(resp);
        resultVO.setResultMsg("成功");
        return resultVO;
    }

    @ApiOperation(value = "查询厅店分销商", notes = "根据参数查询店铺列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/listPartnerShop", method = RequestMethod.POST)
    public ResultVO<Page<PartnerRelShopDTO>> listPartnerShop(@RequestBody PartnerShopListReq req) {
        ResultVO<Page<PartnerRelShopDTO>> resultVO = new ResultVO();
        Page<PartnerRelShopDTO> page = partnerShopService.pagePartnerNearby(req);
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultData(page);
        resultVO.setResultMsg("成功");
        return resultVO;
    }
    
    @ApiOperation(value = "分页查询厅店", notes = "查询厅店")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryShopPage", method = RequestMethod.POST)
    public ResultVO<Page<PartnerShopDTO>> queryPartnerShopPage(@RequestBody PartnerShopQueryPageReq req) {
    	
    	ResultVO<Page<PartnerShopDTO>> resultVO = new ResultVO<Page<PartnerShopDTO>>();
    	Page<PartnerShopDTO> partnerShops = partnerShopService.queryPartnerShopPage(req);
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultMsg("成功");
        resultVO.setResultData(partnerShops);
        
        return resultVO;
    }

    @ApiOperation(value = "查询厅店列表", notes = "查询厅店")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryShopList", method = RequestMethod.POST)
    public ResultVO<List<PartnerShopDTO>> queryPartnerShopList(@RequestBody PartnerShopQueryListReq req) {

        ResultVO<List<PartnerShopDTO>> resultVO = new ResultVO<List<PartnerShopDTO>>();
        List<PartnerShopDTO> partnerShops = partnerShopService.queryPartnerShopList(req);
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultMsg("成功");
        resultVO.setResultData(partnerShops);

        return resultVO;
    }
    
}