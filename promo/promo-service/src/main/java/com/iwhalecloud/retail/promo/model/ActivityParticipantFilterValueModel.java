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
	private List<Map<String,String>> orgList;

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
	private List<String> orgIds;

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
			if(object.orgList!=null){
				this.orgList = object.orgList;
				this.orgIds = object.orgList.stream().map(e->e.get("orgId")).collect(Collectors.toList());
			}
			if(object.tagList!=null){
				this.tagList = object.tagList;
				this.tagIds = object.tagList.stream().map(e->e.get("tagId")).collect(Collectors.toList());
			}
		}
	}


}
