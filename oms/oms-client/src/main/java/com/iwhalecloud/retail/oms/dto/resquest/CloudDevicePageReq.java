package com.iwhalecloud.retail.oms.dto.resquest;

import com.iwhalecloud.retail.oms.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: lin.wh
 * @Date: 2018/11/1 11:16
 * @Description:
 */

@Data
@ApiModel(value = "终端设备分页查询请求参数对象")
public class CloudDevicePageReq extends PageVO {

    @ApiModelProperty(value = "所属区域")
    private String adscriptionCityArea;

    @ApiModelProperty(value = "所属城市")
    private String adscriptionCity;

    @ApiModelProperty(value = "所属厅店")
    private String adscriptionShop;

    @ApiModelProperty(value = "设备编码")
    private String num;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备状态")
    private String status;

    @ApiModelProperty(value = "在线状态")
    private String onlineType;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "是否绑定云货架")
    private String isBindShelf;
}

