package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lhr 2019-04-03 09:51:30
 */
@Data
public class ReBateActivityListResp implements Serializable {
    private static final long serialVersionUID = 1568230640099225793L;

    /**
     * 营销活动Id
     */
    @ApiModelProperty(value = "营销活动Id")
    private String id;

    /**
     * 营销活动名称
     */
    @ApiModelProperty(value = "营销活动名称")
    private String name;

    /**
     * 营销活动名称
     */
    @ApiModelProperty(value = "营销活动编码")
    private String code;

    /**
     * 发起方：10运营商/20供货商
     */
    @ApiModelProperty(value = "发起方：10运营商/20供货商")
    private String initiator;

    /**
     * 开始时间
     */

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    /**
     * 结束时间
     */

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    /**
     * 状态：1已保存/10待审核/20审核通过/30审核不通过/40已关闭/0已取消
     */
    @ApiModelProperty(value = "状态：1已保存/10待审核/20审核通过/30审核不通过/40已关闭/0已取消")
    private Integer status;

    /**
     * calculationRule
     */
    @ApiModelProperty(value = "calculationRule")
    private java.lang.String calculationRule;
}


