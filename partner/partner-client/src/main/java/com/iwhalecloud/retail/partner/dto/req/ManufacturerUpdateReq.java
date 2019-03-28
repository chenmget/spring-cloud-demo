package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
@ApiModel(value = "更新一个厂商信息请求对象")
public class ManufacturerUpdateReq implements Serializable {

    //属性 begin
    /**
     * 厂商ID
     */
    @ApiModelProperty(value = "厂商ID")
    @NotEmpty(message = "厂商ID不能为空")
    private String manufacturerId;

    /**
     * 厂商编码
     */
    @ApiModelProperty(value = "厂商编码")
    @NotEmpty(message = "厂商编码不能为空")
    private String manufacturerCode;

    /**
     * 厂商名称
     */
    @ApiModelProperty(value = "厂商名称")
    @NotEmpty(message = "厂商名称不能为空")
    private String manufacturerName;

    /**
     * 厂商级别
     */
    @ApiModelProperty(value = "厂商级别")
    private String manufacturerLevel;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 关联账号id
     */
    @ApiModelProperty(value = "关联账号id")
    private String userId;

}