package com.iwhalecloud.retail.oms.dto.resquest.cloud;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: yangbl
 * @Date: 2018/11/8
 * @Description: 云货架终端设备日志
 */

@Data
public class CloudDeviceLogReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属厅店ID")
    private String adscriptionShopId;

    /**
     * 所属城市
     */
    @ApiModelProperty(value = "所属城市")
    private String adscriptionCity;

    /**
     * 所属城区
     */
    @ApiModelProperty(value = "所属城区")
    private String adscriptionCityArea;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    /**
     * 统计按日/按周
     */
    @ApiModelProperty(value = "统计按日0/按周1")
    private Integer countState;

}

