package com.iwhalecloud.retail.warehouse.util;

import com.ztesoft.zop.ZopClient;
import com.ztesoft.zop.common.Consts;
import com.ztesoft.zop.common.message.RequestParams;
import com.ztesoft.zop.common.message.ResponseResult;

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
}
