package com.iwhalecloud.retail.web.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.web.annotation.GoodsRankingsAnnotation;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.MemberContext;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import com.iwhalecloud.retail.web.utils.ResponseComUtil;
import com.iwhalecloud.retail.order.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order.consts.order.LoginUserType;
import com.iwhalecloud.retail.order.dto.ResultVO;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.*;
import com.iwhalecloud.retail.order.service.MemberOrderOpenService;
import com.iwhalecloud.retail.order.service.OperatorOrderOpenService;
import com.iwhalecloud.retail.order.service.OrderManagerOpenService;
import com.iwhalecloud.retail.partner.service.PartnerShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin
@RequestMapping("/api/orderManager")
@Slf4j
public class OrderManagerController {

    @Reference
    private OrderManagerOpenService orderManagerOpenService;

    @Reference
    private OperatorOrderOpenService operatorOrderOpenService;

    @Reference
    private MemberOrderOpenService memberOrderOpenService;

    @Reference
    private PartnerShopService partnerShopService;

    @RequestMapping(value = "/selectOrder", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO selectOrder(@RequestBody SelectOrderRequest request) {
        request.checkPage();
        ResultVO resultVO = new ResultVO();
        try {
            CommonResultResp resp ;
            //登录身份过滤
            LoginUserType loginUserType=LoginUserType.matchOpCode(ResponseComUtil.getLoginUserType());
            switch (loginUserType) {
                case LOGIN_USER_TYPE_M: //会员
                    request.setMemberId(MemberContext.getMemberId());
                    request.setLoginUserType(LoginUserType.LOGIN_USER_TYPE_M.getCode());
                    resp = memberOrderOpenService.selectOrder(request);
                    break;
                case LOGIN_USER_TYPE_O: //运营人员
                    request.setUserId(UserContext.getUser().getRelCode());
                    request.setLoginUserType(LoginUserType.LOGIN_USER_TYPE_O.getCode());
                    resp = operatorOrderOpenService.selectOrder(request);
                    break;
                case LOGIN_USER_TYPE_S: //供应商查询
                    request.setSupplyId(UserContext.getUser().getRelCode());
                    request.setLoginUserType(LoginUserType.LOGIN_USER_TYPE_S.getCode());
                    resp = operatorOrderOpenService.selectOrder(request);
                    break;
                case LOGIN_USER_TYPE_SM: //超级管理员
                    request.setLoginUserType(LoginUserType.LOGIN_USER_TYPE_SM.getCode());
                    resp = operatorOrderOpenService.selectOrder(request);
                    break;
                default:
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    String message="loginUserType 不匹配,请确认登录身份:";
                    log.info(message+",userInfo{},request{}",
                            JSON.toJSONString(UserContext.getUser()),JSON.toJSONString(request));
                    resultVO.setResultMsg(message);
                    return resultVO;
            }

            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            ResponseComUtil.orderRespToResultVO(resp, resultVO);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping(value = "/selectOrderDetail", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO selectOrderDetail(@RequestBody SelectOrderRequest request) {
        request.checkPage();
        ResultVO resultVO = new ResultVO();
        try {
            CommonResultResp  dataResp;
            //登录身份过滤
            LoginUserType loginUserType=LoginUserType.matchOpCode(ResponseComUtil.getLoginUserType());
            switch (loginUserType) {
                case LOGIN_USER_TYPE_M: //会员
                    request.setMemberId(MemberContext.getMemberId());
                    request.setLoginUserType(LoginUserType.LOGIN_USER_TYPE_M.getCode());
                    dataResp = memberOrderOpenService.selectOrderDetail(request);
                    break;
                case LOGIN_USER_TYPE_O: //运营人员
                    dataResp= operatorOrderOpenService.selectOrderDetail(request);
                    break;
                case LOGIN_USER_TYPE_S: //供应商查询
                    dataResp = operatorOrderOpenService.selectOrderDetail(request);
                    break;
                case LOGIN_USER_TYPE_SM: //超级管理员
                    dataResp = operatorOrderOpenService.selectOrderDetail(request);
                    break;
                default:
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    String message="loginUserType 不匹配,请确认登录身份:";
                    log.info(message+",userInfo{},request{}",
                            JSON.toJSONString(UserContext.getUser()),JSON.toJSONString(request));
                    resultVO.setResultMsg(message);
                    return resultVO;
            }
            ResponseComUtil.orderRespToResultVO(dataResp, resultVO);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @UserLoginToken
    @RequestMapping(value = "/builderOrder", method = RequestMethod.POST)
    public ResultVO builderOrder(@RequestBody BuilderOrderRequest request) {

        ResultVO resultVO = new ResultVO();
        request.setUserSessionId(MemberContext.getUserSessionId());
        request.setMemberId(MemberContext.getMemberId());

        try {
            CommonResultResp resp = orderManagerOpenService.builderOrder(request);
            ResponseComUtil.orderRespToResultVO(resp, resultVO);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }

        return resultVO;
    }

    @UserLoginToken
    @RequestMapping(value = "/updateOrderStatus", method = RequestMethod.PUT)
    @GoodsRankingsAnnotation
    public ResultVO updateOrderStatus(@RequestBody UpdateOrderStatusRequest request) {
        ResultVO resultVO = new ResultVO();
        request.setMemberId(MemberContext.getMemberId());
        request.setUserSessionId(MemberContext.getUserSessionId());

        try {
            CommonResultResp resp = orderManagerOpenService.updateOrderStatus(request);
            ResponseComUtil.orderRespToResultVO(resp, resultVO);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }

        return resultVO;
    }

    @UserLoginToken
    @RequestMapping(value = "/handlerOrder", method = RequestMethod.PUT)
    @GoodsRankingsAnnotation
    public ResultVO handlerOrder(@RequestBody OrderInfoEntryRequest request) {
        ResultVO resultVO = new ResultVO();
        request.setMemberId(MemberContext.getMemberId());
        request.setUserSessionId(MemberContext.getUserSessionId());

        try {
            CommonResultResp resp = orderManagerOpenService.orderHandler(request);
            ResponseComUtil.orderRespToResultVO(resp, resultVO);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }

        return resultVO;
    }

    @UserLoginToken
    @RequestMapping(value = "/sendGoods", method = RequestMethod.PUT)
    public ResultVO sendGoods(@RequestBody SendGoodsRequest request) {
        ResultVO resultVO = new ResultVO();
        request.setMemberId(MemberContext.getMemberId());
        request.setUserSessionId(MemberContext.getUserSessionId());

        try {
            CommonResultResp resp = orderManagerOpenService.sendGoods(request);
            ResponseComUtil.orderRespToResultVO(resp, resultVO);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }

        return resultVO;
    }

}
