package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author he.sw
 * @Date 2019/06/15
 **/
@Data
@ApiModel(value = "根据申请单批量更新申请单详情状态对象")
public class ResourceReqDetailUpdateReq implements Serializable {

    private static final long serialVersionUID = -1L;

    //@NotEmpty(message = "营销资源申请单项标识不能为空")
    @ApiModelProperty(value = "营销资源申请单项标识")
    private List<String> mktResReqItemIdList;

    @ApiModelProperty(value = "状态")
    private String statusCd;

    @ApiModelProperty(value = "修改人")
    private String updateStaff;

    @ApiModelProperty(value = "每次修改的时间。")
    private java.util.Date updateDate = new Date();

    @ApiModelProperty(value = "状态变更的时间。")
    private java.util.Date statusDate  = new Date();

    @ApiModelProperty(value = "记录营销资源申请单明细标识")
    private List<String> mktResReqDetailIdList;

    @ApiModelProperty(value = "审核说明")
    private String remark;

    @ApiModelProperty(value = "记录首次创建的时间。")
    private java.util.Date createDate;
}
