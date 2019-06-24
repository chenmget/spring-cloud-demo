package com.iwhalecloud.retail.order.lte.service.impl;

import com.iwhalecloud.retail.net.FinishCallBack;
import com.iwhalecloud.retail.net.HttpConnectionClient;
import com.iwhalecloud.retail.order.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.lte.dto.LetNumReqDTO;
import com.iwhalecloud.retail.order.lte.service.PreHandlerNbrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class PreHandlerNbrServiceImpl implements PreHandlerNbrService {
    @Override
    public CommonResultResp getAccessToken(LetNumReqDTO letNumReqDTO) {
        CommonResultResp resp=new CommonResultResp();
        String url="";
        String params="";
        if(StringUtils.isEmpty(url)){
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            return resp;
        }
        HttpConnectionClient.doPost(url, params, new FinishCallBack() {
            @Override
            public void success(String message) {
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            }

            @Override
            public void failure(String message) {
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            }
        });
        return resp;
    }

    @Override
    public CommonResultResp OrdReleaseNum(LetNumReqDTO letNumReqDTO) {
        CommonResultResp resp=new CommonResultResp();
        String url="";
        String params="";
        if(StringUtils.isEmpty(url)){
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            return resp;
        }
        HttpConnectionClient.doPost(url, params, new FinishCallBack() {
            @Override
            public void success(String message) {
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            }

            @Override
            public void failure(String message) {
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            }
        });
        return resp;
    }

}
