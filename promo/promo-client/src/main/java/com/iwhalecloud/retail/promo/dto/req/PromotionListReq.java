package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
@ApiModel(value = "添加公告通知请求对象")
public class PromotionListReq implements Serializable {

    private static final long serialVersionUID = 4014101082500779154L;

    @ApiModelProperty(value = "通知类型 1：业务类 2：热点消息 3：通知公告")
    @NotEmpty(message = "通知类型不能为空")
    private String noticeType;

    @ApiModelProperty(value = "优惠类型：10减免20券30返利40赠送50红包")
    private java.lang.Byte promotionType;


}
