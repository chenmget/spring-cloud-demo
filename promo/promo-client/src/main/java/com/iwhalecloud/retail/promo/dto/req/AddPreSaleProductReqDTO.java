package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhou.zc
 * @date 2019年02月26日
 * @Description:添加预售活动产品配置
 */
@Data
@ApiModel(value = "添加预售活动产品配置")
public class AddPreSaleProductReqDTO extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = -5831262496203376018L;

    /**
     * 预售产品信息
     */
    @ApiModelProperty(value = "预售产品信息")
    List<ActivityProductReq> activityProductReqs;

    /**
     * 预售活动id
     */
    @ApiModelProperty(value = "预售活动id")
    private String marketingActivityId;

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

}
