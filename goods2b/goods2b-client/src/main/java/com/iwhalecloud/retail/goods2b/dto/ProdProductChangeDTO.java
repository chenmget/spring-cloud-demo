package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2019/5/16.
 */
@Data
@ApiModel(value = "对应模型PROD_PRODUCT_CHANGE, 对应实体prodProductChange类")
public class ProdProductChangeDTO implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "变更流水")
    private String changeId;

    @ApiModelProperty(value = "版本号")
    private Long verNum;

    @ApiModelProperty(value = "产品基本信息ID")
    private String productBaseId;

    @ApiModelProperty(value = "审核状态")
    private String auditState;

    @ApiModelProperty(value = "创建时间")
    private java.util.Date createDate;

    @ApiModelProperty(value = "创建人")
    private String createStaff;

    @ApiModelProperty(value = "prodProductChangeDetailDTOs")
    private List<ProdProductChangeDetailDTO> prodProductChangeDetailDTOs;
}
