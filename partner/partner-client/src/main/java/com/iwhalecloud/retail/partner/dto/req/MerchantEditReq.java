package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;


@Data
@ApiModel("修改厂商的信息")
public class MerchantEditReq  implements Serializable {

    private static final long serialVersionUID = 8387771270991914977L;
    //企业信息
    @NotEmpty(message = "公司名称不能为空")
    @ApiModelProperty(value = "公司名称")
    private String merchantName;

    @NotEmpty(message = "法人姓名不能为空")
    @ApiModelProperty(value = "法人姓名")
    private String legalPerson;

    @NotEmpty(message = "商家Id")
    @ApiModelProperty(value = "商家id")
    private String merchantId;

    @NotEmpty(message = "公司地址不能为空")
    @ApiModelProperty(value = "公司地址")
    private String address;

    @NotEmpty(message = "联系方式不能为空")
    @ApiModelProperty(value = "联系方式")
    private String phoneNo;

    @ApiModelProperty(value = "地区")
    private String lanId;







}
