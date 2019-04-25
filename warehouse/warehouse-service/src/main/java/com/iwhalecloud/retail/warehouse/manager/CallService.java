package com.iwhalecloud.retail.warehouse.manager;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.namespace.QName;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.iwhalecloud.retail.warehouse.dto.request.InventoryChangeReq;

/**
 * 串码补入调用webService
 */
@Component
public class CallService {  

	@Value("${webService.inventoryUrl}")
	private String inventoryUrl;
	
	/**
	 * 拼接XML的方式
	 */
	public String postInvenChangeToWebService(InventoryChangeReq req) throws Exception {
		String url = "http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx?wsdl";
		StringBuffer data = new StringBuffer();
		data.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" ");
		data.append("xmlns:web=\"http://WebXml.com.cn/\"> \n");
		data.append("<soapenv:Header/>\n");
		data.append("\t<soapenv:Body>\n");
		data.append("\t  <web:getMobileCodeInfo>\n");
		data.append("\t  <web:mobileCode>");
		data.append("18297747350");// 参数
		data.append("</web:mobileCode>\n");
		data.append("\t  <web:userID></web:userID>\n");
		data.append("\t   </web:getMobileCodeInfo>\n");
		data.append("\t  </soapenv:Body>\n");
		data.append("</soapenv:Envelope>");
		String body = data.toString();

		URL u = new URL(inventoryUrl);
		HttpURLConnection httpURLConnection = (HttpURLConnection) u.openConnection();
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);// 设置该连接是可以输出的
		httpURLConnection.setRequestMethod("POST");// 设置请求方式
		httpURLConnection.setRequestProperty("Charset", "UTF-8");
		httpURLConnection.setUseCaches(false);// 不允许缓存
		httpURLConnection.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
		httpURLConnection.setRequestProperty("accept", "application/xml");
		httpURLConnection.setRequestProperty("Content-Length", String.valueOf(body.getBytes().length));
		DataOutputStream pw = new DataOutputStream(new BufferedOutputStream(httpURLConnection.getOutputStream()));
		// pw.writeBytes(body);
		pw.write(body.getBytes());// 字符串较长
		pw.flush();
		pw.close();

		// 获取返回数据
		BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {// 读取数据
			sb.append(line + "\n");
		}
		br.close();

		String result = getInvenChangeResponseBody(httpURLConnection, sb.toString());

		System.out.println("返回结果3：" + result);
		
		return result;
	}

	/**
	 * 解析返回结果
	 */
	private String getInvenChangeResponseBody(HttpURLConnection connection, String msg) throws Exception {
		String result = null;
		if (connection != null) {
			if (connection.getResponseCode() == 200) {
				Document doc = (Document) DocumentHelper.parseText(msg);
				Element root = doc.getRootElement();
				Element body = root.element("Body");
				Element ns2 = body.element("getMobileCodeInfoResponse");
				Element json = ns2.element("getMobileCodeInfoResult");
				result = json.getTextTrim();
			} else {
				System.out.println("调用结果异常，异常代码:"
						+ connection.getResponseCode());
			}
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		try {
			CallService callService = new CallService();
			InventoryChangeReq req = new InventoryChangeReq();
			callService.postInvenChangeToWebService(req);// 拼接XML的方式
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
} 
