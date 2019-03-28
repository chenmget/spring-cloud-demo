package com.iwhalecloud.retail.system.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel("设置用户状态")
public class UserSetStatusReq implements Serializable {
    private static final long serialVersionUID = 948364833393155026L;

    /**
     * 账号
     */
    @NotEmpty(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID")
    private String userId;

    /**
     * 账号 不能用@NotEmpty
     */
    @NotNull(message = "状态值不能为空")
    @ApiModelProperty(value = "状态  1有效、 0 禁用   2 无效（删除）")
    private Integer statusCd;

}
