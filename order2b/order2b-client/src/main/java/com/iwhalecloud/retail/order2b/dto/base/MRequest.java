package com.iwhalecloud.retail.order2b.dto.base;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MRequest extends OrderRequest implements Serializable {


    @ApiModelProperty(value = "对应sys_user表，user_ID", hidden = true)
    private String userId;

    @ApiModelProperty(value = "对应par_merchant表与user_id 相绑定,merchant_ID（买家的）", hidden = true)
    private String userCode;


}
