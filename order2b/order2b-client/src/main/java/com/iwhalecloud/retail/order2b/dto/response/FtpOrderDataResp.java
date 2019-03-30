package com.iwhalecloud.retail.order2b.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/29 21:52
 */
@Data
public class FtpOrderDataResp implements Serializable {

    @ApiModelProperty("订客户编号")
    private String custId;

    @ApiModelProperty("客户营业执照号")
    private String cerId;

    @ApiModelProperty("经营省份区域编码")
    private String provinceNo;

    @ApiModelProperty("营业执照失效日期")
    private String bizLicExprYrMon;

    @ApiModelProperty("首笔订单发生日期")
    private String fstTransYrMon;

    @ApiModelProperty("平台注册日期")
    private String regYrMon;

    @ApiModelProperty("交易月份")
    private String yrMon;

    @ApiModelProperty("交易笔数")
    private String transAmt;

    @ApiModelProperty("交易台数")
    private String goodsCnt;

    @ApiModelProperty("旧平台客户编号")
    private String oldCustId;



}
