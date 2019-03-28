package com.iwhalecloud.retail.oms.dto.resquest.cloud;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/17 15:57
 * @Description: 云货架终端设备实体类
 */

@Data
public class CloudDeviceReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "cloudShelfNumber")
    private String cloudShelfNumber;

    @ApiModelProperty(value = "设备编码")
    private String num; //设备编码:全局唯一

    public String getCloudShelfNumber() {
        return cloudShelfNumber;
    }

    public void setCloudShelfNumber(String cloudShelfNumber) {
        this.cloudShelfNumber = cloudShelfNumber;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}

