package com.iwhalecloud.retail.web.controller.cloud;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.service.OperatingPositionBindService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.BindContentDTO;
import com.iwhalecloud.retail.oms.dto.BindProductDTO;
import com.iwhalecloud.retail.web.controller.BaseController;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/18 15:09
 * @Description: 运营位管理
 */

@RestController
@RequestMapping("/api/operationPosition")
public class OperatingPositionBindController extends BaseController {
	@Reference
    public OperatingPositionBindService operatingPositionBindService;

    @RequestMapping(value = "/createBindProduct", method = RequestMethod.POST)
    public ResultVO createBindProduct(@RequestBody BindProductDTO dto) {
        ResultVO resultVO = new ResultVO();
        try {
            operatingPositionBindService.createBindProduct(dto);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData("绑定成功");
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping(value = "/createBindContent", method = RequestMethod.POST)
    public ResultVO createBindContent(@RequestBody BindContentDTO dto) {
        ResultVO resultVO = new ResultVO();
        try {
            operatingPositionBindService.createBindContent(dto);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData("绑定成功");
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }
}

