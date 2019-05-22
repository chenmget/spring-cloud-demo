package com.iwhalecloud.retail.order2b.authpay.service;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order2b.authpay.handler.BestpayHandler;
import com.iwhalecloud.retail.order2b.authpay.util.ZopClientUtil;
import com.ztesoft.zop.common.message.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangbo2018
 * @version Id: BestpayServiceTemp.java, v 0.1 2018/3/2 11:35 yangbo2018 Exp $$
 */
@Slf4j
public abstract class BestpayServiceTemp {
    private BestpayHandler bestpayHandler;

    public BestpayHandler getBestpayHandler() {
        return bestpayHandler;
    }

    public void setBestpayHandler(BestpayHandler bestpayHandler) {
        this.bestpayHandler = bestpayHandler;
    }

    public final Map<String, Object> invoke(String method, Object object, String platformCode, String iv, String zopSecret, String zopUrl) throws Exception {
        Map<String, Object> request = zopConvert(object, platformCode, iv);
//      String request = doConvert(object, platformCode, iv);
//      log.info("请求翼支付报文：" + request);
        log.info("请求能开翼支付报文：" + JSONObject.fromObject(request).toString());
//      String response = doService("http://116.228.151.160:18183/preAuthorizationApply", request);
        String response = zopService(method, zopUrl, request, zopSecret);
        log.info("翼支付响应报文：" + response);
        boolean verifyResult = doVerify(response);
        log.info("验签结果:" + verifyResult);

        JsonConfig config = new JsonConfig();
        config.setIgnoreDefaultExcludes(true);
        JSONObject jsonObject = JSONObject.fromObject(response, config);
        String sign = (String) jsonObject.get("sign");
        String data = jsonObject.get("data").toString();

        Map<String, Object> result = new HashMap<String, Object>();
        if (verifyResult & "000000".equals(JSONObject.fromObject(data).get("code"))) {
            result.put("flag", true);
            result.put("originalTransSeq", JSONObject.fromObject(data).get("result"));
        } else {
            result.put("flag", false);
        }

        return result;
    }

    protected abstract Map<String,Object> zopConvert(Object object, String platformCode, String iv)throws Exception;

    protected abstract String doConvert(Object object, String platformCode, String iv) throws Exception;

    protected abstract boolean doVerify(String response);

    private String doService(String Url, String request) {
        log.info("请求翼支付的URL地址：" + Url);
        log.info("发送给翼支付的http请求报文:" + request);
        HttpPost httpPost = new HttpPost(Url);
        httpPost.setHeader("Content-Type", "application/json");
        StringEntity se = new StringEntity(request, "utf-8");
        httpPost.setEntity(se);
        String responseStr = null;
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        try {
            HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
            responseStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            closeableHttpClient.close();
            log.info("从翼支付收到的http响应报文:" + responseStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseStr;
    }

    private String zopService(String method, String zopUrl, Object request, String zopSecret) {
        String version = "1.0";
        ResponseResult responseResult = ZopClientUtil.callRest(zopSecret, zopUrl, method, version, request);
        String resCode = "00000";
        String returnStr = null;
        if (resCode.equals(responseResult.getRes_code())) {
            Object result = responseResult.getResult();
            returnStr = String.valueOf(result);
        }
       return returnStr;
    }

}
