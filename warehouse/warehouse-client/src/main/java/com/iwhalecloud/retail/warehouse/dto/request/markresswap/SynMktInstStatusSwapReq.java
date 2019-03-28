package com.iwhalecloud.retail.warehouse.dto.request.markresswap;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/6 18:49
 */
@Data
@ApiModel("同步串码实例的变更信息到零售商仓库请求参数")
public class SynMktInstStatusSwapReq  implements Serializable {

    @ApiModelProperty(value = "服务编码")
    private String serviceCode;
    @NotEmpty(message = "本地网不能为空")
    @ApiModelProperty(value = "本地网")
    private String lanId;
    @NotEmpty(message = "串码不能为空")
    @ApiModelProperty(value = "串码")
    private String barCode;

}