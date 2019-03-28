package com.iwhalecloud.retail.entity;

import java.io.Serializable;
import java.util.List;


/**
 * 图片识别结果dto
 *
 */
public class VisitDataEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;// 访客id
	
	private String msgid;// 消息 id，类型是字符串(String)

	private String type;// 消息类型：0 为正常；1 为重复抓拍；2 为店员；

	private String equno;// 设备编码，类型是字符串(String)

	private String imageTime;// 抓拍时间，格式是 yyyy-MM-dd HH:mm:ss，类型是字符串(String)

	private String sex;// 性别，男或女，类型是字符串(String)

	private String age;// 年龄，类型是整型(int)

	private String faceFrame;// 图片路径，前面加上域名就可以直接请求获取，类型是字符串(String)

	private List<Integer> personIds;// VIP 识别列表，json 数组，该数组的第一个是首位命中识别出来的 VIP 的 id，
								// 数组中每一个元素的类型都为整型(int)
	private String personIdsStr;
	
	private List<Integer> repeats;// 回头客识别 id，json 数组(元素类型整型 int),如果是回头客则数组内放置的是一个
							// 回头客的 id,否则数组为空.
	private String repeatsStr;
	private String star;// 星级

	private String state;// 回复消息在原有的消息上面加一个返回的状态（"state": 200）表示成功

	

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEquno() {
		return equno;
	}

	public void setEquno(String equno) {
		this.equno = equno;
	}

	public String getImageTime() {
		return imageTime;
	}

	public void setImageTime(String imageTime) {
		this.imageTime = imageTime;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getFaceFrame() {
		return faceFrame;
	}

	public void setFaceFrame(String faceFrame) {
		this.faceFrame = faceFrame;
	}

	

	

	public List<Integer> getPersonIds() {
		return personIds;
	}

	public void setPersonIds(List<Integer> personIds) {
		this.personIds = personIds;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPersonIdsStr() {
		return personIdsStr;
	}

	public void setPersonIdsStr(String personIdsStr) {
		this.personIdsStr = personIdsStr;
	}

	public List<Integer> getRepeats() {
		return repeats;
	}

	public void setRepeats(List<Integer> repeats) {
		this.repeats = repeats;
	}

	public String getRepeatsStr() {
		return repeatsStr;
	}

	public void setRepeatsStr(String repeatsStr) {
		this.repeatsStr = repeatsStr;
	}

	

	
	
}
