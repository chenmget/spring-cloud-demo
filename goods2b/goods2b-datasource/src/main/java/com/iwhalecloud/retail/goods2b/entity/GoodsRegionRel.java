package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * GoodsRegionRel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("prod_goods_region_rel")
@ApiModel(value = "对应模型prod_goods_region_rel, 对应实体GoodsRegionRel类")
@KeySequence(value="seq_prod_goods_region_rel_id",clazz = String.class)
public class GoodsRegionRel implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_goods_region_rel";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * relId
  	 */
	@TableId
	@ApiModelProperty(value = "relId")
  	private String relId;
  	
  	/**
  	 * goodsId
  	 */
	@ApiModelProperty(value = "goodsId")
  	private String goodsId;
  	
  	/**
  	 * regionId
  	 */
	@ApiModelProperty(value = "regionId")
  	private String regionId;
  	
  	/**
  	 * regionName
  	 */
	@ApiModelProperty(value = "regionName")
  	private String regionName;

	/**
	 * lanId
	 */
	@ApiModelProperty(value = "lanId")
	private String lanId;
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** relId */
        relId,
        /** goodsId */
        goodsId,
        /** regionId */
        regionId,
        /** regionName */
        regionName,
		/** lanId */
		lanId
    }

	

}
