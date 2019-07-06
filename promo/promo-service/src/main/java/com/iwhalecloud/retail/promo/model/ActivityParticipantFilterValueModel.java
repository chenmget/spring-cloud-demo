package com.iwhalecloud.retail.promo.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/** 营销活动参与对象过滤条件json字段对应模型
 * ActivityParticipantFilterValueDTO
 * @author lp
 */
@Data
public class ActivityParticipantFilterValueModel {

  	private static final long serialVersionUID = 1L;

	/**
	 * 原Json值
	 */
	private String filterValue;

	/**
	 * 城市列表
	 */
	private List<Map<String,String>> cityList;

	/**
	 * 区县列表
	 */
	private List<Map<String,String>> countyList;

	/**
	 * 标签列表
	 */
	private List<Map<String,String>> tagList;

	/**
	 * 城市id列表
	 */
	private List<String> cityIds;

	/**
	 * 区县id列表
	 */
	private List<String> countyIds;

	/**
	 * 标签id列表
	 */
	private List<String> tagIds;

	/**
	 * 转换结果标识
	 */
	private boolean isSuccess;

	/**
	 * 空构造器
	 */
	public ActivityParticipantFilterValueModel(){

	}

	/**
	 * 构造器，具备json转换功能
	 * @param filterValue
	 */
	public ActivityParticipantFilterValueModel(String filterValue){
		this.filterValue = filterValue;
		ActivityParticipantFilterValueModel object = new ActivityParticipantFilterValueModel();
		try {
			JSONObject filterValueJson = JSONObject.parseObject(filterValue);
			object = JSONObject.toJavaObject(filterValueJson, ActivityParticipantFilterValueModel.class);
			this.isSuccess = true;
		}catch (Exception e){
			this.isSuccess = false;
		}
		if (isSuccess) {
			if(object.cityList!=null){
				this.cityList = object.cityList;
				this.cityIds = object.cityList.stream().map(e->e.get("regionId")).collect(Collectors.toList());
			}
			if(object.countyList!=null){
				this.countyList = object.countyList;
				this.countyIds = object.countyList.stream().map(e->e.get("regionId")).collect(Collectors.toList());
			}
			if(object.tagList!=null){
				this.tagList = object.tagList;
				this.tagIds = object.tagList.stream().map(e->e.get("tagId")).collect(Collectors.toList());
			}
		}
	}

	public static void main(String[] args) {
//		String filterValue = "{\"cityList\":[{\"regionId\":\"730\",\"regionName\":\"岳阳市\"},{\"regionId\":\"731\",\"regionName\":\"长沙市\"},{\"regionId\":\"735\",\"regionName\":\"郴州市\"}],\"countyList\":[{\"regionId\":\"73003\",\"regionName\":\"临湘市分公司\"},{\"regionId\":\"73004\",\"regionName\":\"华容县分公司\"},{\"regionId\":\"73005\",\"regionName\":\"汨罗市分公司\"},{\"regionId\":\"7352001\",\"regionName\":\"郴州市C网号码段\"},{\"regionId\":\"73509\",\"regionName\":\"汝城县电信分公司\"}],\"tagList\":[{\"tagId\":\"10147734\",\"tagName\":\"主推\"},{\"tagId\":\"1101786839631069186\",\"tagName\":\"热卖\"},{\"tagId\":\"1101787084045746177\",\"tagName\":\"特价\"}]}";
//		JSONObject filterValueJson = JSONObject.parseObject(filterValue);
//		ActivityParticipantFilterValueModel object = JSONObject.toJavaObject(filterValueJson, ActivityParticipantFilterValueModel.class);

		String filterValue2 = "{\"tagList\":[{\"tagId\":\"10147734\",\"tagName\":\"主推\"},{\"tagId\":\"1101786839631069186\",\"tagName\":\"热卖\"},{\"tagId\":\"1101787084045746177\",\"tagName\":\"特价\"}]}";
		ActivityParticipantFilterValueModel object2 = new ActivityParticipantFilterValueModel(filterValue2);
		System.out.printf("-----");

	}

}
