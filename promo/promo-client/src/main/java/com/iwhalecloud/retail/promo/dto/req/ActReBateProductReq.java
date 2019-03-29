package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import com.iwhalecloud.retail.promo.dto.ActActivityProductRuleDTO;
import com.iwhalecloud.retail.promo.dto.ActivityRuleDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lhr 2019-03-25 17:19:30
 */
@Data
@ApiModel(value = "添加返利活动产品配置")
public class ActReBateProductReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 2903923032222108276L;
    /**
     * 返利产品信息
     */
    @ApiModelProperty(value = "返利产品信息")
    List<ActivityProductReq> activityProductReqs;

    /**
     * 营销活动id
     */
    @ApiModelProperty(value = "营销活动id")
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
    private String orgId;

    /**
     * 产品返利规则
     */
    @ApiModelProperty(value = "活动产品规则")
    private ActActivityProductRuleDTO actActivityProductRuleDTO;
    /**
     * calculationRule 返利计算规则
     */
    @ApiModelProperty(value = "calculationRule")
    private String calculationRule;
    /**
     * 活动规则对象
     */
    @ApiModelProperty(value = "活动规则对象")
    private List<ActivityRuleDTO> activityRuleDTOList;

}
