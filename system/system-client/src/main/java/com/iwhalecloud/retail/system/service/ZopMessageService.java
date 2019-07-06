package com.iwhalecloud.retail.system.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.request.NoticeMsgReq;
import com.iwhalecloud.retail.system.dto.request.VerifyCodeGetReq;

public interface ZopMessageService {

    /**
     * 获取验证码
     * @param getReq
     */
    ResultVO sendRegistVerifyCode(VerifyCodeGetReq getReq);

    /**
     * 校验验证码
     * @param phoneNo
     * @param verifyCode
     * @return
     */
    ResultVO checkVerifyCode(String phoneNo,String verifyCode);

    /**
     * 通知短信
     * @return
     */
    ResultVO noticeMsg(NoticeMsgReq msgReq);
}
