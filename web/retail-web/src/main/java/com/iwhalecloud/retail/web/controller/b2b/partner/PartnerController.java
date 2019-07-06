package com.iwhalecloud.retail.web.controller.b2b.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.PartnerDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerPageReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierReq;
import com.iwhalecloud.retail.partner.service.PartnerService;
import com.iwhalecloud.retail.web.controller.BaseController;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/partner")
public class PartnerController extends BaseController {
	
    @Reference
    private PartnerService partnerService;


    @ApiOperation(value = "可供代理商查询", notes = "可供代理商查询返回信息分页")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/querySupplyRel", method = RequestMethod.POST)
    ResultVO<Page<PartnerDTO>> querySupplyRel(@RequestBody @ApiParam( value = "supplierReq", required = true )SupplierReq supplierReq)throws Exception{
        return  partnerService.querySupplyRel(supplierReq);
    }


    /**
     * （准备弃用）
     * 代理商列表分页查询接口.
     * @param page
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    @ApiOperation(value = "代理商列表分页查询接口", notes = "根据在页面上选择的代理商名称，状态查询代理商列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/qryPageList", method = RequestMethod.POST)
    public ResultVO<Page<PartnerDTO>> qryPartnerPageList(@RequestBody @ApiParam( value = "查询条件", required = true )PartnerPageReq page) {
        ResultVO<Page<PartnerDTO>> resultVO = new ResultVO<>();
        Page<PartnerDTO> res = partnerService.pagePartner(page);
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultData(res);
        resultVO.setResultMsg("成功");
        return resultVO;
    }

    /**
     * 代理商列表分页查询接口.
     *
     * @param page
     * @return
     * @author zwl
     * @date 2018/11/15 15:27
     */
    @ApiOperation(value = "代理商列表分页查询接口", notes = "根据在页面上选择的代理商名称，状态查询代理商列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResultVO<Page<PartnerDTO>> partnerPage(@RequestBody @ApiParam( value = "查询条件", required = true )PartnerPageReq page) {
        ResultVO<Page<PartnerDTO>> resultVO = new ResultVO<>();
        Page<PartnerDTO> res = partnerService.pagePartner(page);
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultData(res);
        resultVO.setResultMsg("成功");
        return resultVO;
    }

    /**
     * 代理商查询接口.
     * @return
     * @author zwl
     * @date 2018/12/15 15:27
     */
    @ApiOperation(value = "代理商查询接口", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partnerId", value = "partnerId", paramType = "path", required = true, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/getPartnerById")
    public ResultVO<PartnerDTO> getPartnerById(@RequestParam(name = "partnerId") String partnerId) {
        if (StringUtils.isEmpty(partnerId)){
            return ResultVO.error("代理商ID不能为空！");
        }
        PartnerDTO partnerDTO = partnerService.getPartnerById(partnerId);
        if (partnerDTO == null){
            return ResultVO.error("获取代理商信息失败");
        }
        return ResultVO.success(partnerDTO);
    }
}