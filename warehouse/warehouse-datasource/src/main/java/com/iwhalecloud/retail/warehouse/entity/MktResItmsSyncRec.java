package com.iwhalecloud.retail.warehouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("MKT_RES_ITMS_SYNC_REC")
@ApiModel(value = "MKT_RES_ITMS_SYNC_REC, 对应实体MktResItmsSyncRec类")
public class MktResItmsSyncRec implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TNAME = "MKT_RES_ITMS_SYNC_REC";

	/**
  	 * 营销资源推送标识
  	 */
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "mktResItmsSyncRecId")
    private String mktResItmsSyncRecId;
	/**
  	 * 推送日期
  	 */
    @ApiModelProperty(value = "syncDate")
    private String syncDate;
	/**
  	 * 推送批次
  	 */
    @ApiModelProperty(value = "syncBatchId")
    private String syncBatchId;

	/**
  	 * 推送文件名。
  	 */
    @ApiModelProperty(value = "syncFileName")
    private String syncFileName;

	/**
  	 * 营销资源库存变动事件标识
  	 */
    @ApiModelProperty(value = "mktResEventId")
    private String mktResEventId;

	/**
  	 *  营销资源库存变动事件明细ID
  	 */
    @ApiModelProperty(value = "mktResChngEvtDetailId")
    private String mktResChngEvtDetailId;

	/**
  	 * 记录营销资源实例编码。
  	 */
    @ApiModelProperty(value = "mktResInstNbr")
    private String mktResInstNbr;

	/**
  	 * 品牌ID
  	 */
    @ApiModelProperty(value = "brandId")
    private String brandId;

    /**
  	 * 品牌名称
  	 */
    @ApiModelProperty(value = "brandName")
    private String brandName;

    /**
  	 * 产品型号
  	 */
    @ApiModelProperty(value = "unitType")
    private String unitType;

    /**
  	 * 推送前串码所在本地网
  	 */
    @ApiModelProperty(value = "origLanId")
    private String origLanId;

    /**
  	 * 目标本地网
  	 */
    @ApiModelProperty(value = "destLanId")
    private String destLanId;

    /**
  	 * 串码推送状态 0. 已推送待返回 1. 推送成功 -1. 推送失败
  	 */
    @ApiModelProperty(value = "statusCd")
    private String statusCd;

    /**
  	 * 记录状态变更的时间。
  	 */
    @ApiModelProperty(value = "statusDate")
    private String statusDate;
    
    /**
  	 * 备注
  	 */
    @ApiModelProperty(value = "remark")
    private String remark;

    /**
  	 * 创建人
  	 */
    @ApiModelProperty(value = "createStaff")
    private String createStaff;
    
    /**
  	 * 记录首次创建的时间。
  	 */
    @ApiModelProperty(value = "createDate")
    private String createDate;

}