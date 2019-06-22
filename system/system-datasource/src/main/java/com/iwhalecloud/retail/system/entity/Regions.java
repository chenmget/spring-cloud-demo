package com.iwhalecloud.retail.system.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("SYS_REGIONS")
@ApiModel(value = "SYS_REGIONS, 对应实体Regions类")
@KeySequence(value = "seq_sys_regions_id",clazz = String.class)
public class Regions implements Serializable {
    public static final String TNAME = "SYS_REGIONS";

    @TableId
    @ApiModelProperty(value = "regionId")
    private String regionId;

    @ApiModelProperty(value = "parentRegionId")
    private String parentRegionId;

    @ApiModelProperty(value = "regionPath")
    private String regionPath;

    @ApiModelProperty(value = "regionGrade")
    private Short regionGrade;

    @ApiModelProperty(value = "regionName")
    private String regionName;

    @ApiModelProperty(value = "regionCode")
    private String regionCode;

    /**
     * 区域名称
     */
//    private String localName;
//    private String lanCode;
//    private String sourceFrom;

}