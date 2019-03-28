package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhou.zc
 * @date 2019年02月25日
 * @Description:
 */
@Data
@ApiModel(value = "新增前置补贴产品配置")
public class AddPromotionProductReqDTO extends AbstractRequest implements Serializable{


    private static final long serialVersionUID = -5945949975360955823L;

    @ApiModelProperty(value = "用户id")
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

    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id")
    private String marketingActivityId;

    /**
     * 产品配置信息
     */
    @ApiModelProperty(value = "产品配置信息")
    List<AddPreSubsidyProductReqDTO> addPreSubsidyProductReqDTOS;
}
