package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhou.zc
 * @date 2019年03月21日
 * @Description: 补录活动
 */
@Data
public class AddActSupReq extends AbstractRequest implements Serializable{

    private static final long serialVersionUID = 9096483978371837098L;

    @ApiModelProperty(value = "活动Id")
    private String marketingActivityId;

    @ApiModelProperty(value = "申请凭证")
    private String applyProof;

    @ApiModelProperty(value = "补录描述")
    private String description;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "串码和订单的集合数组")
    private String records;
}
