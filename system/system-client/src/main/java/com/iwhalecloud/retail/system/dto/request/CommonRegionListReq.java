package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("本地区域列表查询 请求对象")
public class CommonRegionListReq implements Serializable {
    private static final long serialVersionUID = 7121728434046699553L;

    /**
     * 公共管理区域标识
     */
    @ApiModelProperty(value = "公共管理区域标识id 集合")
    private List<String> regionIdList;

    @ApiModelProperty(value = "记录上级区域标识。")
    private String parRegionId;

}
