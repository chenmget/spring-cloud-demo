package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 通用组织信息请求实体
 * @author lipeng
 */
@Data
@ApiModel("通用组织信息列表查询 请求对象, 两个条件都为空 默认查湖南的 通用组织信息")
public class CommonOrgListReq implements Serializable {
    private static final long serialVersionUID = 7121728434046699553L;

    /**
     * 通用组织信息标识
     */
    @ApiModelProperty(value = "通用组织信息标识id 集合")
    private List<String> orgIdList;

    @ApiModelProperty(value = "记录上级组织标识。")
    private String parentOrgId;

    @ApiModelProperty(value = "组织名称（模糊查询）")
    private String orgName;

    @ApiModelProperty(value = "记录组织的级别。LOVB=LOC-C-0004")
    private String orgLevel;

    @ApiModelProperty(value = "本地网id")
    private String lanId;

    @ApiModelProperty(value = "本地网id集合")
    private List<String> lanIdList;
}
