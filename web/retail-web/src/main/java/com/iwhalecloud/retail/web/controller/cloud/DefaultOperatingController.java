package com.iwhalecloud.retail.web.controller.cloud;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.DefaultOperatingDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.DefaultOperatingQueryReq;
import com.iwhalecloud.retail.oms.service.DefaultOperationService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/defaultOperation")
public class DefaultOperatingController extends BaseController {
	
    @Reference
    private DefaultOperationService defaultOperationService;


    @RequestMapping(value = "/createDefaultOperation", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO createDefaultOperation(@RequestBody DefaultOperatingDTO defaultOperatingDTO)throws Exception{
        ResultVO resultVO = new ResultVO();
        defaultOperationService.createDefaultOperation(defaultOperatingDTO);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData("新增成功");
        resultVO.setResultMsg("success");
        return  resultVO;
    }


    @RequestMapping(value = "/editDefaultOperation", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO editDefaultOperation(@RequestBody DefaultOperatingDTO defaultOperatingDTO)throws Exception{
        ResultVO resultVO = new ResultVO();
        defaultOperationService.editDefaultOperation(defaultOperatingDTO);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData("更新成功");
        resultVO.setResultMsg("success");
        return  resultVO;
    }

    @RequestMapping(value = "/deleteDefaultOperation", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO deleteDefaultOperation(@RequestBody DefaultOperatingDTO defaultOperatingDTO)throws Exception{
        ResultVO resultVO = new ResultVO();
        defaultOperationService.deleteDefaultOperation(defaultOperatingDTO.getId());
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData("更新成功");
        resultVO.setResultMsg("success");
        return  resultVO;
    }

    @RequestMapping(value = "/queryoperatingPostionListDTO", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO queryoperatingPostionListDTO(@RequestBody DefaultOperatingQueryReq defaultOperatingQueryReq)throws Exception{
        ResultVO resultVO = new ResultVO();
        Page<DefaultOperatingDTO> defaultOperatingDTOPage = defaultOperationService.queryoperatingPostionListDTO(defaultOperatingQueryReq);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(defaultOperatingDTOPage);
        resultVO.setResultMsg("success");
        return  resultVO;
    }

}