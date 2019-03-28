package com.iwhalecloud.retail.oms.quartz.utils;


import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author wu.i
 * @07-28
 * 序列生成器
 *
 */
public class SequenceTools {
	/**
	 * 生成随机数  时间格式+随机数
	 * @param dateFormatType 时间格式
	 * @param randomNum 随机数个数
	 * @return
	 */
	public static String getDateRandom(String dateFormatType, int randomNum) {//365*24
		StringBuilder randomBuffer = new StringBuilder();
		randomBuffer.append(new SimpleDateFormat(dateFormatType).format(new Date(System.currentTimeMillis())));
		randomBuffer.append(getServer_id());randomNum+=2;
		while (randomBuffer.length() - dateFormatType.length() <= randomNum) {
			randomBuffer.append(Math.round(Math.random() * 99));
		}
		return randomBuffer.toString().substring(0, dateFormatType.length() + randomNum);
	}
	
	public static String getServer_id(){
		String serviceid="00";
		if(!StringUtils.isEmpty(System.getProperty("serviceid"))){
			serviceid= System.getProperty("serviceid");
		}
		return serviceid;
	}
	
	
    /**
     * add by wui
	 * 生成随机数  时间格式+随机数
	 * @param dateFormatType 时间格式
	 * @param randomNum 随机数个数
	 * @return
	 */
    static AtomicInteger icInt = new AtomicInteger();
	public static String getFlowDateRandom(String dateFormatType, int randomNum) {//365*24
		StringBuilder randomBuffer = new StringBuilder();
		randomBuffer.append(new SimpleDateFormat(dateFormatType).format(new Date(System.currentTimeMillis())));
		String serv_id =getFlowServer_id();
		randomBuffer.append(serv_id);
//		int currValue =icInt.incrementAndGet();
		int prandomNum = randomNum;
		if(serv_id.length() ==1)//配置一位，则多生成一位随机数
			prandomNum=randomNum+1;
		icInt.compareAndSet(getMaxValue(prandomNum), 0);
		int curValue =icInt.incrementAndGet();
		String curStrValue =curValue+"";
		int leftValueLen = prandomNum-curStrValue.length();
		//补0
		String leftValue ="";
		for (int i = 0; i <leftValueLen; i++) {
			leftValue+="0";
		}
		randomBuffer.append(leftValue+curStrValue);
		return randomBuffer.toString();
	}
	
	/**
     * add by wui
	 * 生成随机数  时间格式+随机数
	 * @param dateFormatType 时间格式
	 * @param randomNum 随机数个数
	 * @return
	 */
    static AtomicInteger ordIcInt = new AtomicInteger();
	public static String getOrderDateRandom(String dateFormatType, int randomNum) {//365*24
		StringBuilder randomBuffer = new StringBuilder();
		randomBuffer.append(new SimpleDateFormat(dateFormatType).format(new Date(System.currentTimeMillis())));
		int prandomNum = randomNum;
//		String serv_id = getServer_id();
//		randomBuffer.append(serv_id);
//		int currValue =icInt.incrementAndGet();
		
//		if(serv_id.length() ==1)//配置一位，则多生成一位随机数
//			prandomNum=randomNum+1;
		ordIcInt.compareAndSet(getMaxValue(prandomNum), 0);
		int curValue =ordIcInt.incrementAndGet();
		String curStrValue =curValue+"";
		int leftValueLen = prandomNum-curStrValue.length();
		//补0
		String leftValue ="";
		for (int i = 0; i <leftValueLen; i++) {
			leftValue+="0";
		}
		randomBuffer.append(leftValue+curStrValue);
		return randomBuffer.toString();
	}
		
	public static int getMaxValue(int number){
		String strValue ="";
		for (int i = 0; i < number; i++) {
			strValue=strValue+"9";
		}
		return (new Integer(strValue).intValue()-10);
	}
	public static String getFlowServer_id(){
		String serviceid="0";
		if(!StringUtils.isEmpty(System.getProperty("serviceid"))){
			serviceid= System.getProperty("serviceid");
		}
		return serviceid;
	}
	
   public static void main(String[] args) {
    	String seq  =SequenceTools.getDateRandom(DateUtil.DATE_FORMAT_7,2).substring(2);
		String mmStr=seq.substring(2,4);
		String ddStr=seq.substring(4,6);
		String day = (new Integer(mmStr)*new Integer(ddStr))+"";
		seq = seq.substring(0,2)+day+seq.substring(6);
     	long lastValue  =new Long(seq).longValue();
     	System.out.println(lastValue);
	}
   
}
