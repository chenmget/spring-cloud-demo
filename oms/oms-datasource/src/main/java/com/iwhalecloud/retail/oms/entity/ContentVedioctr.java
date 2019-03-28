package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 云货架
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("T_CONTENT_VEDIOCTR")
@ApiModel(value = "对应模型T_CONTENT_VEDIOCTR, 对应实体ContentVedioctr类")
public class ContentVedioctr implements Serializable {
    /**表名常量*/
    public static final String TNAME = "T_CONTENT_VEDIOCTR";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ID
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "ID")
  	private java.lang.Long id;
  	
  	/**
  	 * 内容id
  	 */
	@ApiModelProperty(value = "内容id")
  	private java.lang.Long contentId;
  	
  	/**
  	 * 播放控制ID
  	 */
	@ApiModelProperty(value = "播放控制ID")
  	private java.lang.Long playbackId;
  	
  	/**
  	 * 货架选择
  	 */
	@ApiModelProperty(value = "货架选择")
  	private java.lang.String storageNum;
  	
  	/**
  	 * 启动播放时长
  	 */
	@ApiModelProperty(value = "启动播放时长")
  	private java.lang.Long playbackTime;
  	
  	/**
  	 * 播放顺序
  	 */
	@ApiModelProperty(value = "播放顺序")
  	private java.lang.Long playbackSequence;
  	
  	/**
  	 * 是否启用
  	 */
	@ApiModelProperty(value = "是否启用")
  	private java.lang.Long isPlayback;
  	
  	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date gmtCreat;
  	
  	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date gmtModify;
  	
  	
  	//属性 end


    /** 字段名称枚举. */
    public static enum FieldNames {
        /** ID. */
        id("id","ID"),

        /** 内容id. */
        contentId("contentId","CONTENT_ID"),

        /** 播放控制ID. */
        playbackId("playbackId","PLAYBACK_ID"),

        /** 货架选择. */
        storageNum("storageNum","STORAGE_NUM"),

        /** 启动播放时长. */
        playbackTime("playbackTime","PLAYBACK_TIME"),

        /** 播放顺序. */
        playbackSequence("playbackSequence","PLAYBACK_SEQUENCE"),

        /** 是否启用. */
        isPlayback("isPlayback","IS_PLAYBACK"),

        /** 创建时间. */
        gmtCreat("gmtCreat","GMT_CREAT"),

        /** 修改时间. */
        gmtModify("gmtModify","GMT_MODIFY");

        private String fieldName;
        private String tableFieldName;
        FieldNames(String fieldName, String tableFieldName){
            this.fieldName = fieldName;
            this.tableFieldName = tableFieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getTableFieldName() {
            return tableFieldName;
        }
    }



}
