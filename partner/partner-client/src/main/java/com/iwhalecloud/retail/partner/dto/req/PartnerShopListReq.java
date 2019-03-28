package com.iwhalecloud.retail.partner.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author My
 * @Date 2018/11/3
 **/
@Data
@ApiModel(value = "附近厅店查询")
public class PartnerShopListReq extends PageVO {
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String lng = "1";
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String lat = "1";
    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    /**
     * 区域
     */
    @ApiModelProperty(value = "区域")
    private String areaCode;

    @ApiModelProperty(value = "分销商id")
    private java.lang.String partnerId;
    /**
     *范围最小值
     */
    private String latMin;
    /**
     * 范围最大值
     */
    private String latMax;
    /**
     * 范围最小值
     */
    private String lngMin;
    /**
     * 范围最大值
     */
    private String lngMax;
}
