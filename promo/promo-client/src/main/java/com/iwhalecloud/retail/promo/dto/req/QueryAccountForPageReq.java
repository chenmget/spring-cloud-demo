package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/26 15:15
 */
@Data
public class QueryAccountForPageReq extends AbstractPageReq implements Serializable {

    /**
     * 10余额账户、20返利账户、30价保账户
     */
    @ApiModelProperty(value = "账户类型:10余额账户、20返利账户、30价保账户")
    private String acctType;
    /**
     * 记录商家唯一标识，作为外键
     */
    @ApiModelProperty(value = "商家标识")
    private String custId;

    @ApiModelProperty(value = "账户ID")
    private String acctId;

}
