package com.iwhalecloud.retail.util;

import com.alibaba.fastjson.JSONObject;
import com.iwhalecloud.retail.web.common.InfCameraProperties;
import com.iwhalecloud.retail.web.common.RetailConst;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 摄像头接口 http 请求工具类
 * 
 *
 */
public class HttpClientUtilReVa {
	private static HttpClient httpclient = null;
	
	public static  <T> T  doPost(String url, Map<String, String> paramMap, Class<T> clazz, InfCameraProperties infCameraProperties, Map<String,File> files) throws Exception {
		//公共参数
		paramMap.put("phoneNo", infCameraProperties.getPhoneNo());
		paramMap.put("password", infCameraProperties.getPassword());
		paramMap.put("vi", infCameraProperties.getVi());
		
		String key = MD5Marker.sign(paramMap);
		paramMap.put("key", key);
		
		
		
		String jsonString=doPost(url, paramMap, files);
		System.out.println("接口地址"+url+"返回参数"+jsonString);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        
        String success=(String) jsonObject.get("success");
        String message= (String) jsonObject.get("message");
        T bean =null;
        if(RetailConst.INF_REVA_SUCC_CODE.equals(success)) {//成功
        	bean = (T) JSONObject.toJavaObject(jsonObject.getJSONObject("data"), clazz);
        }else {//失败
        	throw new Exception("请求摄像头接口失败，接口地址"+url+"原因："+message);
        }
		return bean;
	}


    /**
     * post请求
     * @param url
     * @param paramMap
     * @param files
     * @return
     */
	public static String doPost(String url, Map<String, String> paramMap, Map<String,File> files) {
		
		PostMethod postMethod = null;
		try {
			
			postMethod = new PostMethod(url);
			for (Entry<String, String> s: paramMap.entrySet()) {
				postMethod.addParameter(s.getKey(), s.getValue());
			}
			HttpMethodParams param = postMethod.getParams();
			
			param.setContentCharset("UTF-8");
			
			if (files!=null&&files.size()>0) {
				Part[] parts=new Part[files.size()+paramMap.size()];
				int n=0;
				for (Map.Entry<String, File> file : files.entrySet()) {
					parts[n]=new FilePart(file.getKey(), file.getValue());
					n++;
				} 
				for (Entry<String, String> s: paramMap.entrySet()) {
					parts[n]=new StringPart(s.getKey(), s.getValue());
					n++;
				}
				RequestEntity requestEntity = new MultipartRequestEntity(parts, param); 
				postMethod.setRequestEntity(requestEntity);
			}
			getHttpClient().executeMethod(postMethod);
			InputStream stream = postMethod.getResponseBodyAsStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			StringBuffer buf = new StringBuffer();
			String line;
			while (null != (line = br.readLine())) {
				buf.append(line).append("\n");
			}
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (postMethod != null) {
				// 释放连接
				postMethod.releaseConnection();
			}
		}
		return null;
	}
	
	public static HttpClient getHttpClient() {
		
		if (null != httpclient) {
			return httpclient;
		} 
		
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();

        params.setParameter(HttpMethodParams.RETRY_HANDLER,

            new DefaultHttpMethodRetryHandler());

        params.setMaxTotalConnections(10);

        params.setDefaultMaxConnectionsPerHost(5);

        params.setSoTimeout(10 * 1000);

        params.setConnectionTimeout(10 * 1000);

        MultiThreadedHttpConnectionManager   multiThreadConnManager = new MultiThreadedHttpConnectionManager();
        multiThreadConnManager.setParams(params);
        multiThreadConnManager.setMaxTotalConnections(100);
   
		httpclient = new HttpClient(multiThreadConnManager);
	
		return httpclient;
	}
	
	 /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @param param 请求参数
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, Map<String, String> param) {
        String result = "";
        BufferedReader in = null;
        try {
            String para = "";
            for (String key : param.keySet()) {
                para += (key + "=" + param.get(key) + "&");
            }
            if (para.lastIndexOf("&") > 0) {
                para = para.substring(0, para.length() - 1);
            }
            String urlNameString = url + "?" + para;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    
    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param param  请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, String> param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            String para = "";
            for (String key : param.keySet()) {
                para += (key + "=" + param.get(key) + "&");
            }
            if (para.lastIndexOf("&") > 0) {
                para = para.substring(0, para.length() - 1);
            }
            String urlNameString = url + "?" + para;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}