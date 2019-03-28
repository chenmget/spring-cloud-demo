package com.iwhalecloud.retail.param.resp.dto;

/**
 * @Description  回头客详情接口-返回参数-每小时客流数据
 * @author zhangJun
 * @date 2018年9月6日
 * @version 1.0
 */
public class RepeatDetailsDto {

	private String mallName; // 区域名称
	private String star;// 星级
	private String gatewayName;// 出入口名称
	private String image_url;// 图片
	private String img_path;
	private String msgid; // 到访 id
	private String type;// 类型：0 为首次到访，1 为回头客
	private String repeat_id; // 回头客 id
	private String img_time;// 抓拍时间
	
	
	
	public String getMallName() {
		return mallName;
	}
	public void setMallName(String mallName) {
		this.mallName = mallName;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getGatewayName() {
		return gatewayName;
	}
	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
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
	public String getRepeat_id() {
		return repeat_id;
	}
	public void setRepeat_id(String repeat_id) {
		this.repeat_id = repeat_id;
	}
	public String getImg_time() {
		return img_time;
	}
	public void setImg_time(String img_time) {
		this.img_time = img_time;
	}
	
	
	
}
