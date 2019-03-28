package com.iwhalecloud.retail.param.resp;

/**
 * @Description 返回参数
 * @author zhangJun
 * @date 2018年9月6日
 * @version 1.0
 */
public class CameraPersonCountResp {
	
	
	private int sumNum;//日总客流量
	private int repeatNum;//回头客
	private int newNum;//新客
	private int vipNum;//会员
	
	
	public int getSumNum() {
		return sumNum;
	}
	public void setSumNum(int sumNum) {
		this.sumNum = sumNum;
	}
	public int getRepeatNum() {
		return repeatNum;
	}
	public void setRepeatNum(int repeatNum) {
		this.repeatNum = repeatNum;
	}
	public int getNewNum() {
		return newNum;
	}
	public void setNewNum(int newNum) {
		this.newNum = newNum;
	}
	public int getVipNum() {
		return vipNum;
	}
	public void setVipNum(int vipNum) {
		this.vipNum = vipNum;
	}
	
	
	
}
