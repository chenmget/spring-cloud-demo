package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
@ApiModel("商家绑定系统用户 请求")
public class MerchantBindUserReq implements Serializable {

    private static final long serialVersionUID = -2771511508677223226L;

    //属性 begin
    @ApiModelProperty(value = "商家ID")
    @NotEmpty(message = "商家ID不能为空")
    private String merchantId;

    /**
     * 商家编码
     */
//    @ApiModelProperty(value = "商家编码")
//    private String merchantCode;

    /**
     * 商家类型:  1 厂商    2 地包商    3 省包商   4 零售商
     */
    @ApiModelProperty(value = "商家类型:  1 厂商    2 地包商    3 省包商   4 零售商")
    @NotEmpty(message = "商家类型不能为空")
    private java.lang.String merchantType;

    /**
     * 关联sys_user表user_id
     */
    @ApiModelProperty(value = "关联sys_user表user_id")
    @NotEmpty(message = "用户ID不能为空")
    private java.lang.String userId;
}