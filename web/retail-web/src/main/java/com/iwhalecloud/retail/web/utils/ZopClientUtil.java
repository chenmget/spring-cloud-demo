package com.iwhalecloud.retail.web.utils;

import com.ztesoft.zop.ZopClient;
import com.ztesoft.zop.common.Consts;
import com.ztesoft.zop.common.message.RequestParams;
import com.ztesoft.zop.common.message.ResponseResult;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/3/4 16:30
 * @description
 */
public class ZopClientUtil extends ZopClient {
    public static ResponseResult callRest(String access_token, String url, String method, String version, Object params) {
        RequestParams requestParams = new RequestParams();
        requestParams.setAccess_token(access_token);
        requestParams.setUrl(url);
        requestParams.setTimeout(Consts.DEFAULT_TIMEOUT);
        return callRest(requestParams, method, version, params, true);
    }
}

