package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
@ApiModel("更新系统用户 关联店员信息")
public class UserUpdateWithShopStaffReq implements Serializable {
    private static final long serialVersionUID = -1045036111261704609L;

    /**
     * 工号ID
     */
    @NotEmpty(message = "用户ID不能为空")
    @ApiModelProperty(value = "工号ID")
    private String userId;

    /**
     * 关联 staffId
     */
    @NotEmpty(message = "店员ID不能为空")
    @ApiModelProperty(value = "关联店员的 staffId", required = true)
    private String staffId;

}
