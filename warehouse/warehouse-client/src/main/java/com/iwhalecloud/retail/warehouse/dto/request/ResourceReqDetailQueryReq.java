package com.iwhalecloud.retail.warehouse.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2019/1/11
 **/
@Data
public class ResourceReqDetailQueryReq extends PageVO implements Serializable {

    @ApiModelProperty(value = "记录营销资源申请单标识")
    private String mktResReqId;

    @ApiModelProperty(value = "记录营销资源申请单标识集合")
    private List<String> mktResReqIdList;

    @ApiModelProperty(value = "申请单号")
    private String reqCode;

    @ApiModelProperty(value = "串码")
    private List<String> mktResInstNbrs;

    @ApiModelProperty(value = "记录营销资源申请单明细标识")
    private List<String> mktResReqDetailIds;

    @ApiModelProperty(value = "产品类型")
    private String typeId;

    @ApiModelProperty(value = "厂商名称")
    private String merchantName;

    @ApiModelProperty(value = "厂商id")
    private List<String> merchantId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "产品id")
    private List<String> productId;

//    @ApiModelProperty(value = "品牌名称")
//    private String brandName;


    @ApiModelProperty(value = "产品基本信息表里的品牌ID")
    private String brandId;

    @ApiModelProperty(value = "实列状态")
    private String statusCd;

    @ApiModelProperty(value = "申请开始时间")
    private String createStartDate;

    @ApiModelProperty(value = "申请结束时间")
    private String createEndDate;

    @ApiModelProperty(value = "审核开始时间")
    private String statusStartDate;

    @ApiModelProperty(value = "审核结束时间")
    private String statusEndDate;

    @ApiModelProperty(value = "审核人id")
    private String updateStaff;

    @ApiModelProperty(value = "申请人id")
    private String createStaff;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "申请单id")
    private String reqType;

    @ApiModelProperty(value = "是否查询总条数")
    private Boolean searchCount = true;
}
