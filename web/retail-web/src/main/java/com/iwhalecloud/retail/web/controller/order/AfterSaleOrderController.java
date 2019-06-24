package com.iwhalecloud.retail.web.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.order.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order.dto.ResultVO;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.order.ApplyOrderRequestDTO;
import com.iwhalecloud.retail.order.dto.resquest.order.SelectApplyOrderRequestDTO;
import com.iwhalecloud.retail.order.dto.resquest.order.UpdateApplyOrderRequestDTO;
import com.iwhalecloud.retail.order.service.AfterSaleOrderOpenService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.MemberContext;
import com.iwhalecloud.retail.web.utils.ResponseComUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rop/orderManager")
@Slf4j
public class AfterSaleOrderController {

    @Reference
    private AfterSaleOrderOpenService afterSaleOrderOpenService;

    @UserLoginToken
    @RequestMapping(value = "/addOrderApply", method = RequestMethod.POST)
    public ResultVO addOrderApply(@RequestBody ApplyOrderRequestDTO request) {
        ResultVO resultVO = new ResultVO();

        request.setMemberId(request.getMemberId());
        request.setUserSessionId(MemberContext.getUserSessionId());

        try {
            CommonResultResp resp = afterSaleOrderOpenService.addOrderApply(request);
            ResponseComUtil.orderRespToResultVO(resp, resultVO);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }

        return resultVO;
    }

    @UserLoginToken
    @RequestMapping(value = "/updateTHOrderApply", method = RequestMethod.PUT)
    public ResultVO updateTHOrderApply(@RequestBody UpdateApplyOrderRequestDTO request) {
        ResultVO resultVO = new ResultVO();

        request.setUserSessionId(MemberContext.getUserSessionId());

        try {
            CommonResultResp resp = afterSaleOrderOpenService.updateTHApplyOrder(request);
            ResponseComUtil.orderRespToResultVO(resp, resultVO);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }

        return resultVO;
    }

    @UserLoginToken
    @RequestMapping(value = "/updateHHOrderApply", method = RequestMethod.PUT)
    public ResultVO updateHHOrderApply(@RequestBody UpdateApplyOrderRequestDTO request) {
        ResultVO resultVO = new ResultVO();

        request.setUserSessionId(MemberContext.getUserSessionId());

        try {
            CommonResultResp resp = afterSaleOrderOpenService.updateHHApplyOrder(request);
            ResponseComUtil.orderRespToResultVO(resp, resultVO);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }

        return resultVO;
    }


    @UserLoginToken
    @RequestMapping(value = "/selectOrderApply", method = RequestMethod.POST)
    public ResultVO selectOrderApply(@RequestBody SelectApplyOrderRequestDTO request) {
        request.checkPage();
        ResultVO resultVO = new ResultVO();

        request.setMemberId(MemberContext.getMemberId());

        try {
            CommonResultResp resp = afterSaleOrderOpenService.selectApplyOrder(request);
            resultVO.setResultCode(resp.getResultCode());
            resultVO.setResultMsg(resp.getResultMsg());
//            resultVO.setResultData(ResponseComUtil.page(request.getPageNo(), resp.getPage()));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }

        return resultVO;
    }
}
