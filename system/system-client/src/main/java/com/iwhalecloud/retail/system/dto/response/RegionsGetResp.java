package com.iwhalecloud.retail.system.dto.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/23
 **/
@Data
@ApiModel(value = "SYS_REGIONS")
public class RegionsGetResp implements Serializable {
	
    private static final long serialVersionUID = 1L;
    /**
     * 区域ID
     */
    @ApiModelProperty(value = "区域ID")
    @TableId(type = IdType.ID_WORKER)
    private String regionId;
    /**
     * 父级Id
     */
    @ApiModelProperty(value = "父级Id")
    private String parentRegionId;
    /**
     * 区域路径
     */
    private String regionPath;
    /**
     * 区域等级
     */
    private String regionGrade;
    /**
     * 区域名称
     */
    private String regionName;
    /**
     * 国际标准省市区位码
     */
    private String regionCode;
}
