package com.iwhalecloud.retail.warehouse.dto.response;

import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2019/1/10
 **/
@Data
public class ResourceRequestResp implements Serializable {
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 记录营销资源申请单标识
     */
    @ApiModelProperty(value = "记录营销资源申请单标识")
    private String mktResReqId;

    /**
     * 申请单编码
     */
    @ApiModelProperty(value = "申请单编码")
    private String reqCode;

    /**
     * 申请单内容描述
     */
    @ApiModelProperty(value = "申请单内容描述")
    private String content;
    /**
     * 调出方
     */
    @ApiModelProperty(value = "调出方")
    private String originName;
    /**
     * 调入方
     */
    @ApiModelProperty(value = "调入方")
    private String targetName;
    /**
     * 产品分类
     */
    @ApiModelProperty(value = "产品分类")
    private String productCatName;
    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer quantity;
    /**
     * 记录首次创建的时间。
     */
    @ApiModelProperty(value = "记录首次创建的时间。")
    private java.util.Date createDate;

    @ApiModelProperty(value = "记录首次创建的用户标识。")
    private String createStaff;

    /**
     * 记录状态。LOVB=RES-C-0010
     */
    @ApiModelProperty(value = "记录状态。LOVB=RES-C-0010")
    private String statusCd;

    /**
    * 调拨明细
    */
    @ApiModelProperty(value = "调拨明细")
    private List<ResourceReqDetailDTO> resourceReqDetails;

    /**
     * 调出商家名称
     */
    @ApiModelProperty(value = "调出商家名称")
    private String merchantName;
    /**
     * 调出商家名称
     */
    @ApiModelProperty(value = "调出商家类型")
    private String merchantNameType;
    /**
     * 调入商家名称
     */
    @ApiModelProperty(value = "调入商家类型")
    private String destMerchantNameType;
    /**
     * 调出商家ID
     */
    @ApiModelProperty(value = "调出商家ID")
    private String merchantId;

    /**
     * 目标营销资源仓库标识
     */
    @ApiModelProperty(value = "源营销资源仓库")
    private java.lang.String mktResStoreId;

    /**
     * 目标营销资源仓库
     */
    @ApiModelProperty(value = "目标营销资源仓库")
    private java.lang.String destStoreId;
}
