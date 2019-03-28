package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import com.iwhalecloud.retail.promo.dto.resp.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author li.xinhang
 * @date 2019/3/5
 */
@Data
@ApiModel("根据登录用户查询活动列表入参")
public class MarketingActivityByMerchantListReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户信息
     */
    @ApiModelProperty(value = "用户信息")
    private UserDTO userInfo;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 页码
     */
    @ApiModelProperty("页码")
    private Integer pageNo;

    /**
     * 每页数量
     */
    @ApiModelProperty("每页数量")
    private Integer pageSize;

}
