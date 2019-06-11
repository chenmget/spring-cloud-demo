package com.iwhalecloud.retail.report.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wenlong.zhong
 * @date 2019/6/11
 */
@Data
@ApiModel(value = "地包进销存 数据 汇总 分页查询（按地包商的维度)请求对象")
public class SummarySaleBySupplierPageReq extends PageVO implements Serializable {

    private static final long serialVersionUID = 3600240428650033398L;
    @ApiModelProperty(value = "供应商id")
    private String supplierId;

    @ApiModelProperty(value = "供应商编码")
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "本地网地市")
    private String lanId;

    @ApiModelProperty(value = "区县")
    private String regionId;

    @ApiModelProperty(value = "型号id")
    private String productBaseId;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "品牌id")
    private String brandId;

    @ApiModelProperty(value = "开始时间 格式 %Y-%m-%d")
    private String startDate;

    @ApiModelProperty(value = "结束时间 格式 %Y-%m-%d")
    private String endDate;
}
