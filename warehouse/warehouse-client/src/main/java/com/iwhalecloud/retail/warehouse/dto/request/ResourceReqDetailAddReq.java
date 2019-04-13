package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2019/1/10
 **/
@Data
public class ResourceReqDetailAddReq implements Serializable {
    private static final long serialVersionUID = 1L;


    //属性 begin

    /**
     * 营销资源申请单项标识
     */
    @ApiModelProperty(value = "营销资源申请单项标识")
    private java.lang.String mktResReqItemId;

    /**
     * 营销资源实例ID的标识
     */
    @ApiModelProperty(value = "营销资源实例ID的标识")
    private java.lang.String mktResInstId;
    /**
     * 记录营销资源实例编码。
     */
    @ApiModelProperty(value = "记录营销资源实例编码。")
    private java.lang.String mktResInstNbr;
    /**
     * 发货时间，通知发货的时候需要记录发货时间，根据发货时间，如果超过设置的时间，会自动确认收货。
     */
    @ApiModelProperty(value = "发货时间，通知发货的时候需要记录发货时间，根据发货时间，如果超过设置的时间，会自动确认收货。")
    private java.util.Date dispDate;

    /**
     * 到货时间，确认收货的时候记录到货时间
     */
    @ApiModelProperty(value = "到货时间，确认收货的时候记录到货时间")
    private java.util.Date arriveDate;

    /**
     * 记录状态变更的时间。
     */
    @ApiModelProperty(value = "记录状态变更的时间。")
    private java.util.Date statusDate;

    /**
     * 记录状态。LOVB=PUB-C-0001。
     */
    @ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
    private java.lang.String statusCd;

    /**
     * 记录首次创建的用户标识。
     */
    @ApiModelProperty(value = "记录首次创建的用户标识。")
    private java.lang.String createStaff;

    /**
     * 记录首次创建的时间。
     */
    @ApiModelProperty(value = "记录首次创建的时间。")
    private java.util.Date createDate;

    /**
     * 记录每次修改的员工标识。
     */
    @ApiModelProperty(value = "记录每次修改的员工标识。")
    private java.lang.String updateStaff;

    /**
     * 记录每次修改的时间。
     */
    @ApiModelProperty(value = "记录每次修改的时间。")
    private java.util.Date updateDate;

    /**
     * 记录营销资源实例的数量
     */
    @ApiModelProperty(value = "记录营销资源实例的数量")
    private java.lang.Long quantity;

    /**
     * 记录营销资源实例的数量单位，LOVB=RES-C-0011
     */
    @ApiModelProperty(value = "记录营销资源实例的数量单位，LOVB=RES-C-0011")
    private java.lang.String unit;

    /**
     * 记录出入库类型,LOVB=RES-C-0012
     */
    @ApiModelProperty(value = "记录出入库类型,LOVB=RES-C-0012")
    private java.lang.String chngType;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;

    /**
     * 抽检标识
     */
    @ApiModelProperty(value = "抽检标识")
    private String isInspection;
}
