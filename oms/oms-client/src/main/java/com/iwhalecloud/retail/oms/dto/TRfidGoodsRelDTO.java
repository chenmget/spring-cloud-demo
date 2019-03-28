package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel(value = "Rfid商品关联表")
public class TRfidGoodsRelDTO implements Serializable {
	private static final long serialVersionUID = 4763148269856579728L;
	@ApiModelProperty(value = "关系Id")
    private Long relId;
	
	@NotEmpty(message = "rfid不能为空")
    @ApiModelProperty(value = "rfid")
    private String rfid;
    @NotEmpty(message = "商品ID不能为空")
    @ApiModelProperty(value = "商品ID")
    private String goodsId;
    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    @ApiModelProperty(value = "产品ID")
    private String productId;
    @ApiModelProperty(value = "门店ID")
    private String shopId;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    @ApiModelProperty(value = "修改时间")
    private Date updateDate;
    @ApiModelProperty(value = "创建人")
    private String createStaff;
    @ApiModelProperty(value = "修改人")
    private String updateStaff;

}
