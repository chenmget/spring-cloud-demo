package com.iwhalecloud.retail.warehouse.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2019/1/10
 **/
@Data
public class ResourceRequestQueryReq extends PageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 查询表PROD_TYPE使用
     */
    @ApiModelProperty(value = "产品分类")
    private String catId;

    /**
     * 申请开始时间
     */
    @ApiModelProperty(value = "申请开始时间")
    private java.util.Date beginDate;

    /**
     * 申请结束时间
     */
    @ApiModelProperty(value = "申请结束时间")
    private java.util.Date endDate;

    /**
     *  终端串码列表
     */
    @ApiModelProperty(value = "串码实例列表")
    private List<String> instList;

    /**
     *  产品列表
     */
    @ApiModelProperty(value = "产品列表")
    private List<String> productList;

    /**
     * 记录状态。LOVB=RES-C-0010
     */
    @ApiModelProperty(value = "调货状态。LOVB=RES-C-0010")
    private String statusCd;

    /**
     * 申请单号
     */
    @ApiModelProperty(value = "申请单编码")
    private String reqCode;

    /**
     * 申请单类型
     in  调入的
     out 调出的
     */
    @ApiModelProperty(value = "申请单查询类型in 调入的 out 调出的")
    private String qryType;

    /**
     * 目标营销资源仓库标识
     */
    @ApiModelProperty(value = "目标营销资源仓库标识")
    private String mktResStoreId;

    /**
     * 目标营销资源仓库
     */
    @ApiModelProperty(value = "目标营销资源仓库")
    private String destStoreId;


    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

}
