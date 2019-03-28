package com.iwhalecloud.retail.partner.dto.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("根据条件查找 商家账号")
public class MerchantAccountGetReq implements Serializable {
    private static final long serialVersionUID = 3077459820854666095L;

    //属性 begin
    /**
     * 账号ID
     */
    @TableId(type = IdType.ID_WORKER)
    @ApiModelProperty(value = "账号ID")
    private String accountId;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;
}
