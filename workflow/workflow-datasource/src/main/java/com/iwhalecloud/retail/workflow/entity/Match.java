package com.iwhalecloud.retail.workflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Match
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("wf_match")
@ApiModel(value = "对应模型wf_match, 对应实体Match类")
@KeySequence(value = "seq_wf_match_id",clazz = String.class)
public class Match implements Serializable {
    /**表名常量*/
    public static final String TNAME = "wf_match";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ID 
  	 */
	@TableId
	@ApiModelProperty(value = "ID ")
  	private String matchId;
  	
  	/**
  	 * 匹配脚本 
  	 */
	@ApiModelProperty(value = "匹配脚本 ")
  	private String matchScript;
  	
  	/**
  	 * 流程ID 
  	 */
	@ApiModelProperty(value = "流程ID ")
  	private String wfId;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** ID  */
        matchId,
        /** 匹配脚本  */
        matchScript,
        /** 流程ID  */
        wfId
    }

	

}
