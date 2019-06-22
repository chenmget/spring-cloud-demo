package com.iwhalecloud.retail.warehouse.dto.request.markres;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/2 14:42
 */
@Data
@ApiModel("同步串码实例的变更信息到零售商仓库请求参数")
public class SynMktInstStatusReq  implements Serializable {
    @NotEmpty(message = "服务编码不能为空")
    @ApiModelProperty(value = "服务编码")
    private String service_code;
    @NotEmpty(message = "本地网不能为空")
    @ApiModelProperty(value = "本地网")
    private String Lan_id;
    @NotEmpty(message = "串码不能为空")
    @ApiModelProperty(value = "串码")
    private String resource_instance_ids;

}
