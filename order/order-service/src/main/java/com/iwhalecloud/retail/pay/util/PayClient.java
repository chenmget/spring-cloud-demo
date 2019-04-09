package com.iwhalecloud.retail.pay.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iwhalecloud.retail.order.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.pay.dto.PayParamsDTO;
import com.iwhalecloud.retail.pay.dto.request.PayParamsRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PayClient {

    private static String payUrl = "http://202.103.124.16:1081/gateway.do";
    public static String busiChannel = "0001";
    public static String accountId = "1001199999";

    /**
     * [1550525]【安全漏洞修复】明文密码、日志记录、硬编码、密钥明文存储、敏感信息泄露等
     * 1234567890XXXXXTESTXXX
     */
    public static String key = "IYyP1HnS5q6ozeg8TpXh/nZYqYyP3PQZlnmEo/HzoWmW41/Q3QzHt2fPntKBMcbiWlpM2T3ADb93T7K4JlIssA==";
    public static String notifyUrl = "http://10.45.108.41:8083/api/pay/notifyUrl";

//    private static String payUrl="http://134.175.22.23:1081/gateway.do";
//    private static String payUrl="http://134.175.22.26:8080/wpp-epoint/gateway.do";

    public static CommonResultResp toPay(PayParamsDTO params) {
        CommonResultResp resultVO = new CommonResultResp();
        log.info(JSON.toJSONString(params));
        JSONObject paramsJson = JSONObject.parseObject(JSON.toJSONString(params));
//        对象转map
        Map payMap = new HashMap();
        PayUtils.payObjectToMap(payMap, params);

        String sign = PayUtils.createSign(payMap,key);
        paramsJson.put("sign", sign);
        log.info(paramsJson.toJSONString());
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(payUrl);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        StringEntity se = null;
        try {
            se = new StringEntity(paramsJson.toJSONString());
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            //输出调用结果
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                String result = null;
                try {
                    result = EntityUtils.toString(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 生成 JSON 对象
                JSONObject obj = JSONObject.parseObject(result);
                log.info(obj.toJSONString());
                String errorcode = obj.getString("code");
                if ("0000".equals(errorcode)) {
                    JSONObject result_data = obj.getJSONObject("result_data");
                    String toPayUrl = result_data.getString("url");
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                    resultVO.setResultData(toPayUrl);
                    resultVO.setResultMsg("成功");
                } else {
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resultVO.setResultMsg("失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg("请求支付网关出差 " + e.getMessage());
        }
        return resultVO;
    }

    public static void main(String[] args) {
        String param = "{\n" +
                "    \"account_id\": \"1001199999\",\n" +
                "    \"div_details\": \"\",\n" +
                "    \"notify_url\": \"http://10.45.108.41:8083/api/pay/notifyUrl\",\n" +
                "    \"client_ip\": \"127.0.0.1\",\n" +
                "    \"request_time\": \"20160525165649\",\n" +
                "    \"bank_id\": \"WXPAY\",\n" +
                "    \"busi_channel\": \"10011\",\n" +
                "    \"ped_cnt\": \"\",\n" +
                "    \"pay_type\": \"NATIVE\",\n" +
                "    \"limit_pay\": \"\",\n" +
                "    \"request_seq\": \"20160525165648415599\",\n" +
                "    \"pay_amount\": \"1\",\n" +
                "    \"attach_info\": {\n" +
                "        \"tmnum\": \"\",\n" +
                "        \"product_id\": \"\",\n" +
                "        \"attach\": \"\",\n" +
                "        \"busi_code\": \"0001\",\n" +
                "        \"product_desc\": \"\",\n" +
                "        \"cust_id\": \"oZs6bs2YFklYRqWLvAsXyePX_4iY\"\n" +
                "    },\n" +
                "    \"order_info\": {\n" +
                "        \"over_time\": \"\",\n" +
                "        \"order_desc\": \"test\",\n" +
                "        \"order_date\": \"20160519\",\n" +
                "        \"order_id\": \"20160519121234601278\"\n" +
                "    },\n" +
                "    \"pay_channel\": \"\"\n" +
                "}\n";
        PayParamsDTO paramsDTO = JSON.parseObject(param, PayParamsDTO.class);
        PayParamsRequest paramsRequestDTO=new PayParamsRequest();
//        paramsRequestDTO.setBankId("WXPAY");
        paramsRequestDTO.setOrderId("");
        paramsRequestDTO.setOpenId("");
//        paramsRequestDTO.setPayType("JSAPI");
        toPay(paramsDTO);
    }

}
