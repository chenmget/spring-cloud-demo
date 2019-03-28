package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * ContentChkhis
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型t_content_chkhis, 对应实体ContentChkhis类")
@TableName("t_content_chkhis")
public class ContentChkhis implements Serializable {
    /**表名常量*/
    public static final String TNAME = "t_content_chkhis";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * logid
  	 */
	@ApiModelProperty(value = "logid")
	@TableId(type = IdType.ID_WORKER)
  	private java.lang.Long logid;
  	
  	/**
  	 * contentid
  	 */
	@ApiModelProperty(value = "contentid")
  	private java.lang.Long contentid;
  	
  	/**
  	 * commitoprid
  	 */
	@ApiModelProperty(value = "commitoprid")
  	private java.lang.String commitoprid;
  	
  	/**
  	 * commitdate
  	 */
	@ApiModelProperty(value = "commitdate")
  	private java.util.Date commitdate;
  	
  	/**
  	 * oprid
  	 */
	@ApiModelProperty(value = "oprid")
  	private java.lang.String oprid;
  	
  	/**
  	 * chkdate
  	 */
	@ApiModelProperty(value = "chkdate")
  	private java.util.Date chkdate;
  	
  	/**
  	 * result
  	 */
	@ApiModelProperty(value = "result")
  	private java.lang.Integer result;
  	
  	/**
  	 * desp
  	 */
	@ApiModelProperty(value = "desp")
  	private java.lang.String desp;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** logid */
        logid,
        /** contentid */
        contentid,
        /** commitoprid */
        commitoprid,
        /** commitdate */
        commitdate,
        /** oprid */
        oprid,
        /** chkdate */
        chkdate,
        /** result */
        result,
        /** desp */
        desp
    }

	

}
