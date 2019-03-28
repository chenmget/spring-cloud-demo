package com.iwhalecloud.retail.web.zop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.service.zop.ZopClientService;
import com.iwhalecloud.retail.oms.service.zop.ZopConfigStorage;
import com.ztesoft.zop.ZopClient;
import com.ztesoft.zop.common.Consts;
import com.ztesoft.zop.common.exception.CodeException;
import com.ztesoft.zop.common.message.ApiRequest;
import com.ztesoft.zop.common.message.MessageBuilder;
import com.ztesoft.zop.common.message.RequestParams;
import com.ztesoft.zop.common.message.ResponseResult;
import com.ztesoft.zop.common.utils.HttpUtils;
import com.ztesoft.zop.common.utils.UrlUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author Z
 * @date 2018/11/21
 */
@Slf4j
public class ZopClientServiceImpl implements ZopClientService {

    private ZopConfigStorage zopConfigStorage;

    @Override
    public String getAccessToken() {
        //如果缓存中的accessToken未失效，直接返回
        if (!zopConfigStorage.isAccessTokenExpired()) {
            return zopConfigStorage.getAccessToken();
        }

        return this.forceRefreshAccessToken() ;
    }

    @Override
    public String forceRefreshAccessToken() {

        try {
            ResponseResult rs = ZopClient.getAccessTokenByOauth2(zopConfigStorage.getAppId(), zopConfigStorage.getAppSecret());
            System.out.println(rs.getRes_code() + rs.getRes_message());
            log.info("远程获取token返回结果，appId={},res_code={},res_message={}",zopConfigStorage.getAppId(),rs.getRes_code(),rs.getRes_message());

            if (Consts.CODE_00000.equals(rs.getRes_code())) {         //00000 调用成功
                Map<String, String> rule = rs.getResult();
                log.info("远程获取token返回结果成功,result={}", JSONObject.toJSON(rule));
                String access_token = rule.get("access_token");//得到应用授权令牌
                String expiresIn = rule.get("expires_in");    //超时时间

                //设置超时时间
                if ("-1".equals(expiresIn)) {
                    this.zopConfigStorage.setExpiresTime(-1); //目前不清楚该参数的
                } else {
                    this.zopConfigStorage.setExpiresTime(System.currentTimeMillis() + Long.parseLong(expiresIn));
                }

                this.zopConfigStorage.setAccessToken(access_token);

                return access_token;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用能开服务
     * @param method
     * @param version
     * @param params
     * @return
     */
    @Override
    public ResultVO callRest(String method, String version, Object params) {
        return callRest(method, version, params,zopConfigStorage.getTimeout());
    }

    @Override
    public ResultVO callRest(String method, String version, Object params,int timeout) {
        RequestParams requestParams = new RequestParams();
        requestParams.setAccess_token(getAccessToken());
        requestParams.setTimeout(timeout);

        ResponseResult result = callRest(requestParams,method,version,params,true);

        ResultVO rv = new ResultVO();
        rv.setResultData(result.getResult());
        rv.setResultMsg(result.getRes_message());
        if(Consts.CODE_00000.equals(rv.getResultCode())) {
            rv.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        } else {
            rv.setResultCode(result.getRes_code());
        }

        return rv;
    }

    private ResponseResult callRest(RequestParams requestParams, String method, String version, Object params,boolean forceRefreshAccess) {
        requestParams = MessageBuilder.buildRequestParams(requestParams);
        String url = UrlUtils.getRestUrl(requestParams.getUrl());
        ApiRequest apiRequest = MessageBuilder.buildRestRequest(requestParams, method, version, params);
        String request = JSON.toJSONString(apiRequest);
        try {
            String result = HttpUtils.post(url, request, Consts.CONTENT_TYPE_JSON, requestParams.getTimeout().intValue(), requestParams.getHeaders());
            ResponseResult response = (ResponseResult) JSON.parseObject(result, ResponseResult.class);

            if (forceRefreshAccess && response != null && (Consts.CODE_20308.equals(response.getRes_code()) || Consts.CODE_20309.equals(response.getRes_code()))) {
                String access_token = forceRefreshAccessToken();
                requestParams.setAccess_token(access_token);
                return callRest(requestParams, method, version, params, false);
            }
            return response;
        } catch (Exception e) {
            String code = "";
            if ((e instanceof CodeException)) {
                CodeException codeException = (CodeException) e;
                code = codeException.getCode();
            }

            return MessageBuilder.buildRestResponse(code, e.getMessage());
        }

    }

    @Override
    public void setZopConfigStorage(ZopConfigStorage zopConfigStorage) {
        this.zopConfigStorage = zopConfigStorage;
    }

    @Override
    public ZopConfigStorage getZopConfigStorage() {
        return this.zopConfigStorage;
    }
}
