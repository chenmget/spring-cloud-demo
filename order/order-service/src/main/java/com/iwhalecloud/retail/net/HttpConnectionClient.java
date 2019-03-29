package com.iwhalecloud.retail.net;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author gongs
 * @date
 */
@Slf4j
public class HttpConnectionClient {
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * POST请求
     *
     * @param url 请求地址
     */
    public static void doPost(String url, String params, FinishCallBack callBack) {
        OutputStreamWriter out = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            // 超时时间30秒
            conn.connect();

            log.info("url:" + url);
            log.info("params:" + params);
            if (params != null) {
                out = new OutputStreamWriter(conn.getOutputStream(), CHARSET_NAME);
                out.write(params);
                out.flush();
            }
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStreamReader r = new InputStreamReader(conn.getInputStream(), CHARSET_NAME);
                BufferedReader reader = new BufferedReader(r);
                String line;
                StringBuffer stringBuffer = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                System.out.println(stringBuffer.toString());
                callBack.success(stringBuffer.toString());
            } else {
                callBack.failure("error");
            }

        } catch (IOException e) {
            callBack.failure("error");
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
