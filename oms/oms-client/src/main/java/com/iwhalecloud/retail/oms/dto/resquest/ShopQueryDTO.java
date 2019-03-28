package com.iwhalecloud.retail.oms.dto.resquest;

import io.swagger.annotations.ApiModelProperty;

import com.iwhalecloud.retail.oms.dto.SelectBaseRequest;

import lombok.Data;

/**
 * @Author My
 * @Date 2018/10/24
 **/
@Data
public class ShopQueryDTO extends SelectBaseRequest {
	private static final long serialVersionUID = -8885578272058693474L;
	
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
}
