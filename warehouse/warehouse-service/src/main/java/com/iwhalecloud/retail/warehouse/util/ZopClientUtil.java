package com.iwhalecloud.retail.warehouse.util;

import com.ztesoft.zop.ZopClient;
import com.ztesoft.zop.common.Consts;
import com.ztesoft.zop.common.message.RequestParams;
import com.ztesoft.zop.common.message.ResponseResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZopClientUtil extends ZopClient {

    public static ResponseResult callRest(String access_token, String url, String method, String version, Object params) {
        RequestParams requestParams = new RequestParams();
        requestParams.setAccess_token(access_token);
        requestParams.setUrl(url);
        requestParams.setTimeout(Consts.DEFAULT_TIMEOUT);
        return callRest(requestParams, method, version, params, true);
    }
    public static ResponseResult callRest(String access_token, String url, String method, String version,String timeout, Object params) {
        RequestParams requestParams = new RequestParams();
        requestParams.setAccess_token(access_token);
        requestParams.setTimeout(Integer.valueOf(timeout));
        requestParams.setUrl(url);
//        requestParams.setTimeout(Consts.DEFAULT_TIMEOUT);
        return callRest(requestParams, method, version, params, true);
    }
    public static String zopService(String method, String zopUrl, Object request, String zopSecret) {
        String version = "1.0";
        ResponseResult responseResult = ZopClientUtil.callRest(zopSecret, zopUrl, method, version, request);
        String resCode = "00000";
        String returnStr = null;
        if (resCode.equals(responseResult.getRes_code())) {
            Object result = responseResult.getResult();
            returnStr = String.valueOf(result);
        }else{
            log.info("能开请求失败：method：" +method+"，zopUrl:"+ zopUrl+"，resCode:"
                    +responseResult.getRes_code()+"，msg:"+responseResult.getRes_message());
        }
        return returnStr;
    }
}
