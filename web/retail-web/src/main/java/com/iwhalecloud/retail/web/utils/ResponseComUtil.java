package com.iwhalecloud.retail.web.utils;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.response.CommonResultResp;
import com.iwhalecloud.retail.order.consts.order.LoginUserType;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.web.interceptor.MemberContext;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import org.springframework.util.StringUtils;

import static com.iwhalecloud.retail.system.common.SystemConst.*;

public class ResponseComUtil {

    public static void RespToResultVO(CommonResultResp resp, ResultVO result) {
        result.setResultCode(resp.getResultCode());
        result.setResultMsg(resp.getResultMsg());
        result.setResultData(resp.getResultData());
    }

    public static void orderRespToResultVO(com.iwhalecloud.retail.order.dto.base.CommonResultResp resp,
                                           com.iwhalecloud.retail.order.dto.ResultVO result) {
        result.setResultCode(resp.getResultCode());
        result.setResultMsg(resp.getResultMsg());
        result.setResultData(resp.getResultData());
    }

    public static void orderRespToResultVO(com.iwhalecloud.retail.order.dto.base.CommonResultResp resp,
                                          ResultVO result) {
        result.setResultCode(resp.getResultCode());
        result.setResultMsg(resp.getResultMsg());
        result.setResultData(resp.getResultData());
    }

    public static String getLoginUserType() {

        if (!StringUtils.isEmpty(MemberContext.getMemberId())) {
            return LoginUserType.LOGIN_USER_TYPE_M.getCode(); //会员登录
        }
//        AdminUser user = UserContext.getUser();
        UserDTO user = UserContext.getUser();
        if (user == null) {
            return "login error";
        }
        switch (user.getUserFounder()) {
            case USER_FOUNDER_4:
                return LoginUserType.LOGIN_USER_TYPE_S.getCode();  //供应商
            case USER_FOUNDER_3:  //店长
                return LoginUserType.LOGIN_USER_TYPE_O.getCode(); //运营人员
            case USER_FOUNDER_6://店员
                return LoginUserType.LOGIN_USER_TYPE_O.getCode(); //运营人员
            case USER_FOUNDER_1:
                return LoginUserType.LOGIN_USER_TYPE_SM.getCode(); //超级管理员
            default:
                return "login error";
        }
    }

}
