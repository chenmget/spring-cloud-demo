package com.iwhalecloud.retail.web.controller.cloud;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.CloudDeviceDTO;
import com.iwhalecloud.retail.oms.dto.CloudShelfDetailDTO;
import com.iwhalecloud.retail.oms.dto.ShelfDetailDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudShelfActionReq;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudShelfDetailResetReq;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudShelfDetailModifyReq;
import com.iwhalecloud.retail.oms.service.CloudShelfDetailService;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/cloudShelfDetailController")
public class CloudShelfDetailController extends BaseController {
	
    @Reference
    private CloudShelfDetailService cloudShelfDetailService;


    /**
     * 重置云货架栏目接口.
     *
     * @param req
     * @return
     * @author Ji.kai
     * @date 2018/11/1 15:27
     */
    @ApiOperation(value = "重置云货架栏目接口", notes = "重置云货架栏目接口")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ResultVO<List<CloudShelfDetailDTO>> resetCloudShelfDetail(@RequestBody @ApiParam( value = "重置云货架栏目请求参数", required = true )CloudShelfDetailResetReq req) {
        ResultVO<List<CloudShelfDetailDTO>> resultVO = new ResultVO<>();
        log.info("page:{}", JSON.toJSONString(req));
        try {
            List<CloudShelfDetailDTO> res = cloudShelfDetailService.resetCloudShelfDetail(req, UserContext.getUserId());
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(res);
            resultVO.setResultMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }


    @ApiOperation(value = "修改云货架详情", notes = "修改云货架详情")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/modifyCloudShelfDetailByParam", method = RequestMethod.POST)
    public ResultVO modifyCloudShelfDetailByParam(@RequestBody CloudShelfDetailModifyReq cloudShelfDetailModifyReq) {
        return cloudShelfDetailService.modifyCloudShelfDetailByParam(cloudShelfDetailModifyReq);
    }



    @ApiOperation(value = "更新云货架详情", notes = "更新云货架详情")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/updateCloudShelfDetailByAction", method = RequestMethod.POST)
    public ResultVO updateCloudShelfDetailByAction(@RequestBody CloudShelfActionReq cloudShelfActionReq)throws Exception {
        return cloudShelfDetailService.updateCloudShelfDetailByAction(cloudShelfActionReq);
    }


    @ApiOperation(value = "根据商品ID查询云货架详情信息", notes = "根据商品ID查询云货架详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "productId", paramType = "path", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //查询云货架终端设备详情
    @RequestMapping(value = "/queryCloudShelfDetailByProductId/{productId}", method = RequestMethod.GET)
    public ResultVO<CloudDeviceDTO> queryCloudShelfDetailByProductId(@PathVariable String productId) {
        ResultVO resultVO = new ResultVO();
        try {
            List<ShelfDetailDTO>  shelfDetailDTOList = cloudShelfDetailService.queryCloudShelfDetailByProductId(productId);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(shelfDetailDTOList);
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }
}