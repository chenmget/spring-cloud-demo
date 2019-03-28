package com.iwhalecloud.retail.web.controller.rfid;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.TRfidGoodsRelDTO;
import com.iwhalecloud.retail.oms.service.RfidGoodsRelService;

import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.controller.rfid.request.RfidGoodsRelAddReq;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/oms/rfidGoodsRel")
@Api(value="rfid与商品关联关系的控制类",tags={"rfid与商品关联关系"})
public class RfidGoodsRelController extends BaseController {

	@Reference
    private RfidGoodsRelService rfidGoodsRelService;

    /**
     * 新增RfidGoodsRel.
     *
     * @param rfidGoodsRelAddReq
     * @return
     * @author Ji.kai
     * @date 2018/10/24 15:27
     */
    @ApiOperation(value = "新增RfidGoodsRel", notes = "传入RfidGoodRel对象，进行保存操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO<Boolean> addRfidGoodsRel(@RequestBody @ApiParam(value="RfidGoodsRelAddReq", required = true )RfidGoodsRelAddReq rfidGoodsRelAddReq) {
        ResultVO<Boolean> resultVO = new ResultVO<Boolean>();
        log.info("request:{}", JSON.toJSONString(rfidGoodsRelAddReq));

        TRfidGoodsRelDTO dto = new TRfidGoodsRelDTO();
        BeanUtils.copyProperties(rfidGoodsRelAddReq,dto);
        int resp = rfidGoodsRelService.addRfidGoodsRel(dto);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);

        resultVO.setResultData(resp==1);
        resultVO.setResultMsg("成功");
        return resultVO;
    }
    /**
     * 查询RfidGoodsRel.
     *
     * @param rfid
     * @return
     * @author Ji.kai
     * @date 2018/10/24 15:27
     */
    @ApiOperation(value = "查询RfidGoodsRel", notes = "根据rfid，查询rfidGoodsRel对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rfid", value = "rfid", paramType = "path", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/get/{rfid}", method = RequestMethod.GET)
    public ResultVO<TRfidGoodsRelDTO> getRfidGoodsRel(@PathVariable String rfid) {
        ResultVO<TRfidGoodsRelDTO> resultVO = new ResultVO<TRfidGoodsRelDTO>();
        log.info("rfid:{}", JSON.toJSONString(rfid));
        TRfidGoodsRelDTO request = new TRfidGoodsRelDTO();
        request.setRfid(rfid);
        List<TRfidGoodsRelDTO> dtos = rfidGoodsRelService.getRfidGoodsRel(request);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);

        if (dtos!=null && dtos.size()>=1) {
            resultVO.setResultData(dtos.get(0));
        }
        resultVO.setResultMsg("成功");
        return resultVO;
    }
    /**
     * 修改RfidGoodsRel.
     *
     * @param request
     * @return
     * @author Ji.kai
     * @date 2018/10/24 15:27
     */
    @ApiOperation(value = "修改RfidGoodsRel", notes = "接口支持根据rfid修改商品Id")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResultVO<Boolean> updateRfidGoodsRel(@RequestBody TRfidGoodsRelDTO request) {
        ResultVO<Boolean> resultVO = new ResultVO();
        log.info("request:{}", JSON.toJSONString(request));
        int resp = rfidGoodsRelService.modifyRfidGoodsRel(request);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(resp==1);
        resultVO.setResultMsg("成功");
        return resultVO;
    }

    /**
     * 删除RfidGoodsRel.
     *
     * @param relId
     * @return
     * @author Ji.kai
     * @date 2018/10/24 15:27
     */
    @ApiOperation(value = "删除RfidGoodsRel", notes = "根据关系id，进行删除操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "relId", value = "关系ID", paramType = "path", required = true, dataType = "Long")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/delete/{relId}", method = RequestMethod.DELETE)
    public ResultVO<Boolean> deleteRfidGoodsRel(@PathVariable Long relId) {
        ResultVO<Boolean> resultVO = new ResultVO();
        log.info("rfid:{}", JSON.toJSONString(relId));
        TRfidGoodsRelDTO request = new TRfidGoodsRelDTO();
        request.setRelId(relId);
        boolean resp = rfidGoodsRelService.removeRfidGoodsRel(request);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(resp);
        resultVO.setResultMsg("成功");
        return resultVO;
    }

}
