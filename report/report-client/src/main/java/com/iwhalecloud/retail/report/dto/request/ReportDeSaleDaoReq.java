package com.iwhalecloud.retail.report.dto.request;

import java.util.List;

import com.iwhalecloud.retail.dto.PageVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportDeSaleDaoReq extends PageVO {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="itemDateStart")
    private String itemDateStart;//统计时间止
    
    @ApiModelProperty(value="itemDateEnd")
    private String itemDateEnd;//统计时间起
    
    @ApiModelProperty(value="lanIdList")
    private List<String> lanIdList;//地市
    
    @ApiModelProperty(value="productName")
    private String productName;//产品名称
    
    @ApiModelProperty(value="typeId")
    private String typeId;//产品类型
    
    @ApiModelProperty(value="brandId")
    private String brandId;//品牌
    
    @ApiModelProperty(value="productBaseName")
    private String productBaseName;//产品型号
    
    @ApiModelProperty(value="supplierName")
    private String supplierName;//地包商名称
    
    @ApiModelProperty(value="supplierId")
    private String supplierCode;//地包商编码
    
    @ApiModelProperty(value="priceLevel")
    private String priceLevel;//档位
    
    @ApiModelProperty(value="stockWarning")
    private String stockWarning;//库存预警
    
    @ApiModelProperty(value = "supplierId")
	private String supplierId;//地包商ID
    
    
}