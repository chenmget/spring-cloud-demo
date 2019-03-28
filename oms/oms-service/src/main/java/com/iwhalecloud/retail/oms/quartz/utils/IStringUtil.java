package com.iwhalecloud.retail.oms.quartz.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IStringUtil {
	
	/**
	 *  返回true 字符串为null 或 空串 或 只包含空白符 否则返回false
	 * IStringUtil.isEmpty(" ")       = true
	 * IStringUtil.isEmpty("  bob") = false;
	 * @param str
	 * @return
	 */
	public static boolean isEmptyWithOnlyBlank(String str) {
		if (str == null || "".equals(str))
			return true;

		String pattern = "\\S";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(str);
		return !m.find();
	}
	
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	 
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	 
	/**
	 * 将数组成str连接成字符串
	 * 
	 * @param str
	 * @param array
	 * @return
	 */
	public static String implode(String str, Object[] array) {
		if (str == null || array == null) {
			return "";
		}
		String result = "";
		for (int i = 0; i < array.length; i++) {
			if (i == array.length - 1) {
				result += array[i].toString();
			} else {
				result += array[i].toString() + str;
			}
		}
		return result;
	}
	
	public static String implodeValue(String str, Object[] array) {
		if (str == null || array == null) {
			return "";
		}
		String result = "";
		for (int i = 0; i < array.length; i++) {
			if (i == array.length - 1) {
				result += "?";
			} else {
				result += "?" + str;
			}
		}
		return result;
	}
	
	public static String getRandStr(int n) {
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < n; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return sRand;
	}
	
	public static List stringToList(String[]str){
		List ls = new ArrayList();
		if(str!=null && str.length>0){
			for (int i = 0; i < str.length; i++) {
				ls.add(str[i]);
			}
		}
		return ls;
	}
	
	public static final String replaceAll(String line, String str1, String str2) {
        StringBuffer newStr = new StringBuffer();
        int lastAppendIndex=0;
        
        int start=line.indexOf(str1,lastAppendIndex);
        
        if(start==-1)return line;
        
        while(start>-1){
            newStr.append(line.subSequence(lastAppendIndex, start));
            newStr.append(str2);
            lastAppendIndex=start+str1.length();
            start=line.indexOf(str1,lastAppendIndex);
        }
        newStr.append(line.subSequence(lastAppendIndex, line.length()));
        
        return newStr.toString();

    }
	
	public static final String[] split(String line, String separator) {
		int index = 0;
		ArrayList matchList = new ArrayList();

		int start = line.indexOf(separator, index);

		if (start == -1)
			return new String[] { line.toString() };

		while (start > -1) {
			String match = line.subSequence(index, start).toString();
			matchList.add(match);
			index = start + separator.length();
			start = line.indexOf(separator, index);
		}

		matchList.add(line.subSequence(index, line.length()).toString());

		int resultSize = matchList.size();

		while (resultSize > 0 && matchList.get(resultSize - 1).equals(""))
			resultSize--;

		String[] result = new String[resultSize];
		return (String[]) matchList.subList(0, resultSize).toArray(result);
	}
	 
	private IStringUtil() {

	}
}
