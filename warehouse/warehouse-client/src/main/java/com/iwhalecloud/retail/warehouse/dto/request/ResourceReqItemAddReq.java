package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2019/1/10
 **/
@Data
public class ResourceReqItemAddReq implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 记录营销资源申请单标识
     */
    @ApiModelProperty(value = "记录营销资源申请单标识")
    private java.lang.String mktResReqId;

    /**
     * 营销资源标识，记录product_id
     */
    @ApiModelProperty(value = "营销资源标识，记录product_id")
    private java.lang.String mktResId;

    /**
     * 营销资源仓库标识
     */
    @ApiModelProperty(value = "营销资源仓库标识")
    private java.lang.String mktResStoreId;

    /**
     * 记录营销资源实例的数量
     */
    @ApiModelProperty(value = "记录营销资源实例的数量")
    private java.lang.Long quantity;

    /**
     * 本地网标识
     */
    @ApiModelProperty(value = "本地网标识")
    private java.lang.String lanId;

    /**
     * 指向公共管理区域标识
     */
    @ApiModelProperty(value = "指向公共管理区域标识")
    private java.lang.String regionId;

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
     * 记录状态变更的时间。
     */
    @ApiModelProperty(value = "记录状态变更的时间。")
    private java.util.Date statusDate;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;

    /**
     * 串码类型
     */
    @ApiModelProperty(value = "串码类型")
    private String mktResInstType;
}
