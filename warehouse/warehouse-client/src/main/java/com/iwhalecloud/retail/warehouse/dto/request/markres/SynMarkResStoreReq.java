package com.iwhalecloud.retail.warehouse.dto.request.markres;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/7 15:35
 */
@Data
@ApiModel("仓库信息同步接口")
public class SynMarkResStoreReq  implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "操作类型不能为空")
    @ApiModelProperty(value = "操作类型")
    private String actType;
    @NotEmpty(message = "请求数据不能为空")
    @ApiModelProperty(value = "请求数据")
    private SynMarkResStoreItemReq request;
}
