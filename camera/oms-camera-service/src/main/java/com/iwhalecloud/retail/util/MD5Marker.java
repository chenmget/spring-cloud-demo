package com.iwhalecloud.retail.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;


/**
 * md5加密
 * 
 * @author weicheng
 *
 */
public class MD5Marker {
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			// System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			} else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return md5StrBuff.toString();
	}

	public static String sign(Map<String, String> paramMap) {
		TreeMap<String, String> treeMap=new TreeMap<String, String>(paramMap);
		StringBuffer buffer=new StringBuffer();
		for(Map.Entry<String, String> entry : treeMap.entrySet()) {//把传过来的参数(除去key)进行按照字母排序 进行签名
			if(!entry.getKey().equals("key")&&!entry.getKey().equals("imgTagContents")&&!entry.getKey().equals("tagContents")){
					buffer.append(entry.getKey()+"="+entry.getValue()+"&");
			}
		}		
		String md5=buffer.subSequence(0, buffer.toString().length()-1).toString();
		//去掉字符串最后一个&符号
		return getMD5Str(md5);
	}
	
}
