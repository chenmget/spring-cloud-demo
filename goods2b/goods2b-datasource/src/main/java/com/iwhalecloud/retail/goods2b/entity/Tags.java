package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Tags
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_tags, 对应实体ProdTags类")
@KeySequence(value="seq_prod_tags_id",clazz = String.class)
@TableName("prod_tags")
public class Tags implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_tags";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * tagId
  	 */
	@TableId
	@ApiModelProperty(value = "tagId")
  	private String tagId;

	/**
	 * tagType
	 */
	@ApiModelProperty(value = "tagType")
	private String tagType;
  	
  	/**
  	 * tagName
  	 */
	@ApiModelProperty(value = "tagName")
  	private String tagName;

	/**
	 * isDeleted
	 */
	@ApiModelProperty(value = "isDeleted")
	private Integer isDeleted;

	/**
	 * createStaff
	 */
	@ApiModelProperty(value = "createStaff")
	private String createStaff;

	/**
	 * createDate
	 */
	@ApiModelProperty(value = "createDate")
	private Date createDate;

	/**
	 * updateStaff
	 */
	@ApiModelProperty(value = "updateStaff")
	private String updateStaff;

	/**
	 * updateDate
	 */
	@ApiModelProperty(value = "updateDate")
	private Date updateDate;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** tagId */
        tagId,
        /** tagType */
		tagType,
		/** tagName */
		tagName,
		/** isDeleted */
		isDeleted,
		/** createStaff */
		createStaff,
		/** createDate */
		createDate,
		/** updateStaff */
		updateStaff,
		/** updateDate */
		updateDate
    }

	

}
