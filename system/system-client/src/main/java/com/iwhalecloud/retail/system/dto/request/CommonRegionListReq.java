package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("本地区域列表查询 请求对象, 两个条件都为空 默认查湖南的 本地网")
public class CommonRegionListReq implements Serializable {
    private static final long serialVersionUID = 7121728434046699553L;

    /**
     * 公共管理区域标识
     */
    @ApiModelProperty(value = "公共管理区域标识id 集合")
    private List<String> regionIdList;

    /**
     * 父级区域ids
     */
    @ApiModelProperty(value = "父级区域ids 集合")
    private List<String> parRegionIdList;

    @ApiModelProperty(value = "记录上级区域标识。")
    private String parRegionId;

    @ApiModelProperty(value = "区域名称（模糊查询）")
    private String regionName;

    @ApiModelProperty(value = "记录区域的级别。LOVB=LOC-C-0004")
    private String regionLevel;

}
