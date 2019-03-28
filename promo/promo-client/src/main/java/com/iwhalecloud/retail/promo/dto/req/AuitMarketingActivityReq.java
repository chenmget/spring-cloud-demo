package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lhr 2019-03-26 20:18:30
 */
@Data
public class AuitMarketingActivityReq implements Serializable {

    private static final long serialVersionUID = -6858692647541972562L;
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private String id;
    /**
     * 营销活动名称
     */
    @ApiModelProperty(value = "营销活动名称")
    private String name;
    /**
     * 用户Id
     */
    @ApiModelProperty(value = "当前用户")
    private String userId;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "当前用户名称")
    private String userName;

    @ApiModelProperty(value = "岗位名称")
    private String sysPostName;

    /**
     * 关联组织 id
     */
    @ApiModelProperty(value = "关联组织 id")
    private java.lang.String orgId;
}
