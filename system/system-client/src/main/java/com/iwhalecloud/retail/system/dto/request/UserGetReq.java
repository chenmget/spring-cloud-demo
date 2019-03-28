package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("根据条件查找用户")
public class UserGetReq implements Serializable {
    private static final long serialVersionUID = -7300358618371428764L;
    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String loginName;

    /**
     * 工号ID
     */
    @ApiModelProperty(value = "工号ID", required = true)
    private String userId;

    /**
     * 店员ID
     */
    @ApiModelProperty(value = "关联店员的 staffId", required = true)
    private String relNo;

    /**
     * 关联代理商ID或 供应商ID.
     */
    @ApiModelProperty(value = "关联店员的 staffId")
    private String relCode;
}
