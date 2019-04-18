package com.iwhalecloud.retail.order2b.authpay.service;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order2b.authpay.handler.BestpayHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

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

    public final boolean invoke(String url, Object object, String platformCode, String iv) throws Exception {
        String request = doConvert(object, platformCode, iv);
        log.info("请求翼支付报文：" + request);
        String response = doService(url, request);
        log.info("翼支付响应报文：" + response);
        boolean verifyResult = doVerify(response);
        log.info("验签结果:" + verifyResult);
        return verifyResult && "000000".equals(JSON.parseObject(response).get("code"));
    }

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

}
