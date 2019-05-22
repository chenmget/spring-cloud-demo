package com.iwhalecloud.retail.order2b.authpay.service.v3;

import com.iwhalecloud.retail.order2b.authpay.annotation.AES;
import com.iwhalecloud.retail.order2b.authpay.handler.BestpayHandler;
import com.iwhalecloud.retail.order2b.authpay.service.BestpayServiceTemp;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.RandomStringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author yangbo2018
 * @version Id: BestpayServiceV3.java, v 0.1 2018/3/2 11:56 yangbo2018 Exp $$
 */
public class BestpayServiceV3 extends BestpayServiceTemp {
    public BestpayServiceV3(BestpayHandler bestpayHandler) {
        this.bestpayHandler = bestpayHandler;
    }

    private BestpayHandler bestpayHandler;

    @Override
    public BestpayHandler getBestpayHandler() {
        return bestpayHandler;
    }

    @Override
    public void setBestpayHandler(BestpayHandler bestpayHandler) {
        this.bestpayHandler = bestpayHandler;
    }

    @Override
    protected Map<String, Object> zopConvert(Object object, String platformCode, String iv) throws Exception{
        String aesKey = RandomStringUtils.randomAlphanumeric(16);
        String aesEncodedKey = bestpayHandler.rsaEncode(aesKey);
        Map<String, Object> resultMap = new TreeMap<>();
        resultMap.put("platformCode", platformCode);
        resultMap.put("aesEncodedKey", aesEncodedKey);
        Map<String, Object> dataMap = entityToAesMap(object, aesKey, iv);
//        resultMap.put("data", JSONObject.fromObject(dataMap));
//        resultMap.put("data", dataMap);
        String data = JSONObject.fromObject(dataMap).toString();
        String sign = bestpayHandler.sign(data, BestpayHandler.SIGNATURE_ALGORITHM_SHA1);
        resultMap.put("sign", sign);
        resultMap.putAll(JSONObject.fromObject(dataMap));
//        return JSONObject.fromObject(resultMap).toString();
        return resultMap;
    }

    @Override
    protected String doConvert(Object object, String platformCode, String iv) throws Exception {
        String aesKey = RandomStringUtils.randomAlphanumeric(16);
        String aesEncodedKey = bestpayHandler.rsaEncode(aesKey);
        Map<String, Object> resultMap = new TreeMap<>();
        resultMap.put("platformCode", platformCode);
        resultMap.put("aesEncodedKey", aesEncodedKey);
        Map<String, Object> dataMap = entityToAesMap(object, aesKey, iv);
        String data = JSONObject.fromObject(dataMap).toString();
        resultMap.put("data", JSONObject.fromObject(dataMap));
        String sign = bestpayHandler.sign(data, BestpayHandler.SIGNATURE_ALGORITHM_SHA1);
        resultMap.put("sign", sign);
        return JSONObject.fromObject(resultMap).toString();
    }

    @Override
    protected boolean doVerify(String response) {
        JsonConfig config = new JsonConfig();
        config.setIgnoreDefaultExcludes(true);
        JSONObject jsonObject = JSONObject.fromObject(response, config);
        String sign = (String) jsonObject.get("sign");
        JSONObject dataJsonObject = JSONObject.fromObject(jsonObject.get("data"), config);
        //固定顺序
        String data = orderJson(dataJsonObject);
        System.out.println(data);
        return bestpayHandler.verify(data, sign, BestpayHandler.SIGNATURE_ALGORITHM_SHA1);

    }

    private Map<String, Object> entityToAesMap(Object object, String aesKey, String iv) {
        Map<String, Object> map = new TreeMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            //所有字段均需要加密
            String key = field.getName();
            Object innerObject = null;
            try {
                innerObject = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (innerObject != null) {
                ClassLoader loader = innerObject.getClass().getClassLoader();
                if (loader == null) {
                    String value = String.valueOf(innerObject);
                    if (value != null & !"".equals(value)) {
                        if (field.isAnnotationPresent(AES.class)) {
                            try {
                                value = bestpayHandler.encode(aesKey, value, iv);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        map.put(key, value);
                    }
                } else {
                    System.out.println(innerObject);
                    Map<String, Object> temp = entityToAesMap(innerObject, aesKey, iv);
                    map.put(key, JSONObject.fromObject(temp));
                }

            }
        }
        return map;

    }

    /**
     * 防止能开传过来的dataJson顺序不一样导致验签失败
     */
    private String orderJson(JSONObject dataJsonObject){
        String code = (String)dataJsonObject.get("code");
        String dataStr = null;
        if("000000".equals(code)) {
            dataStr = "{\"code\":\""+dataJsonObject.getString("code")+"\",\"msg\":\""+dataJsonObject.getString("msg")+"\"," +
                    "\"result\":\""+dataJsonObject.getString("result")+"\"}";
        }else{
            dataStr = "{\"code\":\""+dataJsonObject.getString("code")+"\",\"msg\":\""+dataJsonObject.getString("msg")+"\"}";
        }
        return dataStr;
    }
}
