package com.iwhalecloud.retail.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * MktResStoreTemp
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型MKT_RES_STORE_TEMP, 对应实体MktResStoreTemp类")
public class MktResStoreTempDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private String mktResStoreId;
	
	/**
  	 * 仓库名称
  	 */
	@ApiModelProperty(value = "仓库名称")
  	private String mktResStoreName;
	
	/**
  	 * 盘存时间
  	 */
	@ApiModelProperty(value = "盘存时间")
  	private java.util.Date checkDate;
	
	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date createDate;
	
	/**
  	 * 上级仓库标识
  	 */
	@ApiModelProperty(value = "上级仓库标识")
  	private Long parStoreId;
	
	/**
  	 * 组织标识
  	 */
	@ApiModelProperty(value = "组织标识")
  	private Long orgId;
	
	/**
  	 * 营销资源仓库管理员ID
  	 */
	@ApiModelProperty(value = "营销资源仓库管理员ID")
  	private Long staffId;
	
	/**
  	 * 区域标识
  	 */
	@ApiModelProperty(value = "区域标识")
  	private Long regionId;
	
	/**
  	 * 渠道业务编码
  	 */
	@ApiModelProperty(value = "渠道业务编码")
  	private Long channelId;
	
	/**
  	 * 仓库类型
  	 */
	@ApiModelProperty(value = "仓库类型")
  	private String storeType;
	
	/**
  	 * 状态
  	 */
	@ApiModelProperty(value = "状态")
  	private String statusCd;
	
	/**
  	 * 状态时间
  	 */
	@ApiModelProperty(value = "状态时间")
  	private java.util.Date statusDate;
	
	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String remark;
	
	/**
  	 * 仓库编码
  	 */
	@ApiModelProperty(value = "仓库编码")
  	private String mktResStoreNbr;
	
	/**
  	 * 2.0多出无名氏
  	 */
	@ApiModelProperty(value = "2.0多出无名氏")
  	private String storageCode1;
	
	/**
  	 * 2.0多出无名氏
  	 */
	@ApiModelProperty(value = "2.0多出无名氏")
  	private String storageCode2;
	
	/**
  	 * 本地网标识
  	 */
	@ApiModelProperty(value = "本地网标识")
  	private Long lanId;
	
	/**
  	 * 2.0多出无名氏
  	 */
	@ApiModelProperty(value = "2.0多出无名氏")
  	private Long operId;
	
	/**
  	 * 2.0多出无名氏
  	 */
	@ApiModelProperty(value = "2.0多出无名氏")
  	private String address;
	
	/**
  	 * 2.0多出无名氏
  	 */
	@ApiModelProperty(value = "2.0多出无名氏")
  	private String vbatchcode;
	
	/**
  	 * 物资类型
  	 */
	@ApiModelProperty(value = "物资类型")
  	private Long rcType;
	
	/**
  	 * 2.0多出无名氏
  	 */
	@ApiModelProperty(value = "2.0多出无名氏")
  	private Long familyId;
	
	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private Long createStaff;
	
	/**
  	 * 普通(0)、县级直供中心(1)、新小偏网点(2)
  	 */
	@ApiModelProperty(value = "普通(0)、县级直供中心(1)、新小偏网点(2)")
  	private String directSupply;
	
	/**
  	 *  2.0多出无名氏
  	 */
	@ApiModelProperty(value = " 2.0多出无名氏")
  	private String provider;
	
	/**
  	 * 2.0多出无名氏
  	 */
	@ApiModelProperty(value = "2.0多出无名氏")
  	private String providerName;
	
	/**
  	 * 回收仓库标识
  	 */
	@ApiModelProperty(value = "回收仓库标识")
  	private Long recStoreId;
	
	/**
  	 * 回收方式
  	 */
	@ApiModelProperty(value = "回收方式")
  	private String recType;
	
	/**
  	 * 回收期限
  	 */
	@ApiModelProperty(value = "回收期限")
  	private Long recDay;
	
	/**
  	 * 仓库细类
  	 */
	@ApiModelProperty(value = "仓库细类")
  	private String storeSubType;
	
	/**
  	 *  仓库层级
  	 */
	@ApiModelProperty(value = " 仓库层级")
  	private String storeGrade;
	
	/**
  	 * 生效时间
  	 */
	@ApiModelProperty(value = "生效时间")
  	private java.util.Date effDate;
	
	/**
  	 * 失效时间
  	 */
	@ApiModelProperty(value = "失效时间")
  	private java.util.Date expDate;
	
	/**
  	 * 修改人
  	 */
	@ApiModelProperty(value = "修改人")
  	private Long updateStaff;
	
	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date updateDate;
	
	/**
  	 * 同步状态:1已同步，0未同步
  	 */
	@ApiModelProperty(value = "同步状态:1已同步，0未同步")
  	private String synStatus;
	
	/**
  	 * synDate
  	 */
	@ApiModelProperty(value = "synDate")
  	private java.util.Date synDate;
	
  	
}
